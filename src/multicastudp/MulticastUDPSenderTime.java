package multicastudp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MulticastSocket;
import java.net.*;

public class MulticastUDPSenderTime {
    final static int port = 5000;
    // Which address
    final static String group = "225.4.5.6";
    final static int ttl = 1;
    
    public static void main(String[] args) throws UnknownHostException, InterruptedException, IOException {
        MulticastSocket s = new MulticastSocket();
        byte buf[] = new byte[1000];  
        try (DatagramSocket pack  = new DatagramSocket()) {
            String kontinu;
            String msg;
            msg = "tes";
            int i = 0;
            //pack.setSoTimeout(100);
            //msgPacket.setSoTimeout(100);
            
            while(true){
                try
                {
                    DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length,InetAddress.getByName(group), port);
                    msg = "tes"+i;
                    i++;
                    s.send(msgPacket,(byte)ttl);
                    Thread.sleep(500);
                    //s.close();
                }
                
                catch(SocketTimeoutException e){
                    System.out.println("Timeout reached!!! " + e);
                    s.close();
                }   
            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
