package multicastudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Receiver2 {
    final static int port = 5000;
    // Which address
    final static String group = "225.4.5.6";
    static int i=0;

    public static void main(String[] args) throws UnknownHostException, IOException {
        try (MulticastSocket s = new MulticastSocket(port)){
            //s.setSoTimeout(TIMEOUT);
            // join the multicast group
            s.joinGroup(InetAddress.getByName(group));
            
            byte buf[ ] = new byte[1024];
            String name [] = new String[12];
            
            //name [12] = "Hendro";
            DatagramPacket pack = new DatagramPacket(buf, buf.length);
        
            
            while(true){
               
                s.receive(pack);
                String msg = new String(buf, 0, buf.length);
                System.out.println("Received data from: " + pack.getAddress().toString() +
		    ":" + pack.getPort() + " with length: " +
		    pack.getLength());
                
                System.out.println("Received msg: " + msg + " from " + pack.getAddress()+ " count: "+i+"\n");
                i++;
                
            }
        
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
