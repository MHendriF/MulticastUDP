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

public class Pengirim {
    public static void main(String[] args) throws IOException {
 
        //Address
        String multiCastAddress = "224.0.0.1";
        final int multiCastPort = 52684;
        String my_ip;
        
        //Get ip address
        my_ip = InetAddress.getLocalHost().getHostAddress();
        
        //Create Socket
        System.out.println("Create socket on address " + multiCastAddress + " and port " + multiCastPort + ".");
        InetAddress group = InetAddress.getByName(multiCastAddress);
        MulticastSocket s = new MulticastSocket(multiCastPort);
        s.joinGroup(group);
 
        //Prepare Data
        Message message = new Message(0, "esss", my_ip, "10.151.32.32", 0, "2017/06/1 04:49:20", 40.366633, 74.640832);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(baos);
        os.writeObject(message);
        byte[] data = baos.toByteArray();
 
        //Send data
        s.send(new DatagramPacket(data, data.length, group, multiCastPort));
    }
}
