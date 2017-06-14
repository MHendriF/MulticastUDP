import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;

public class Pengirim {
    public static void main(String[] args) throws IOException {
 
        String multiCastAddress = "224.1.10.2";
        final int multiCastPort = 5268;
        String my_ip;
        String pesan="", tujuan="", flag="", temp1;
        int max_hop;
        char flag2;
        
        try{
            //Get ip address
            my_ip = InetAddress.getLocalHost().getHostAddress();
        
            //Create Socket
            System.out.println("Create socket on address " + multiCastAddress + " and port " + multiCastPort + ".");
            InetAddress group = InetAddress.getByName(multiCastAddress);
            MulticastSocket s = new MulticastSocket(multiCastPort);
            s.joinGroup(group);

            //Prepare Data
            while(true){
                pesan = JOptionPane.showInputDialog("Masukkan Pesan");
                tujuan = JOptionPane.showInputDialog("Masukkan IP Tujuan");
                temp1 = JOptionPane.showInputDialog("Set Maksimal Hop");
                
                max_hop = Integer.parseInt(temp1);
                
                Message message = new Message(0, pesan, my_ip, tujuan, 0, max_hop, "2017/06/9 04:49:20", 40.366633, 74.640832);
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(baos);
                os.writeObject(message);
                byte[] data = baos.toByteArray();

                //Send data
                s.send(new DatagramPacket(data, data.length, group, multiCastPort));
                System.out.println("Broadcast message : " + "'"  + pesan + "'" + " to " + tujuan +" with max hop = "+max_hop);
                flag = JOptionPane.showInputDialog("Continue?(Y/N)");
                {
                    flag2 = flag.charAt(0);
                    if((flag2 != 'Y') && (flag2 != 'y'))
                        break;
                }
                
            }
            
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
}
