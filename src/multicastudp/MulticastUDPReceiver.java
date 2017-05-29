package multicastudp;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.*;

public class MulticastUDPReceiver {
   
    final static int port = 5000;
    // Which address
    final static String group = "225.4.5.6";

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

                System.out.write(pack.getData(),0,pack.getLength());
                //System.out.println("received msg: " + msg + "from " + pack.getAddress());
                System.out.println();
                
                //s.leaveGroup(InetAddress.getByName(group));
                //s.close();
            }
        
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
}
