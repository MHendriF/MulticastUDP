package multicastudp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MulticastSocket;
import java.net.*;

public class MulticastUDPSender {
    final static int port = 5000;
    // Which address
    final static String group = "225.4.5.6";
    final static int ttl = 1;
    
    public static void main(String[] args) throws UnknownHostException, InterruptedException, IOException {
        MulticastSocket s = new MulticastSocket();
        byte buf[] = new byte[1024];
        
        try (DatagramSocket pack  = new DatagramSocket()) {
            String kontinu;
            String msg;
            String ip_tujuan;
            ip_tujuan = "10.151.32.22";
            int hop = 0;
            
            while(true) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Message: ");
                    msg = in.readLine();
                    msg = msg + ":" + ip_tujuan + ":" + hop;
                    DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length,InetAddress.getByName(group), port);
                    s.send(msgPacket,(byte)ttl);
                    Thread.sleep(1000);

                    //s.close();

                    System.out.println("Sender sent packet with msg: " + msg);
                    System.out.print("Lanjut? y/n: ");
                    kontinu = in.readLine();
                    if (kontinu.startsWith("n")) {
                        //s.leaveGroup(InetAddress.getByName(group));
                        s.close();
                        break;
                    }
                }
                catch(SocketTimeoutException e){
                    System.out.println("Timeout reached!!! " + e);
                }
                
            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
