/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicastudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class MulticastUDPReceiverTime {
    final static int port = 5000;
    final static String group = "225.4.5.6";
    final static int TIMEOUT = 2000; 

    public static void main(String[] args) throws UnknownHostException, IOException {
        try (MulticastSocket s = new MulticastSocket(port)){
            //s.setSoTimeout(TIMEOUT);
            // join the multicast group
            s.joinGroup(InetAddress.getByName(group));
            
            byte buf[ ] = new byte[1024];
            String name [] = new String[12];
            
            //name [12] = "Hendro";
            DatagramPacket pack = new DatagramPacket(buf, buf.length);
            //DatagramSocket p = new DatagramSocket(5000, InetAddress.getByName(group));
            //p.setSoTimeout(TIMEOUT);
            
            while(true){
                try {
                    s.receive(pack);
                    String msg = new String(buf, 0, buf.length);
                    System.out.println("Received data from: " + pack.getAddress().toString() +
                        ":" + pack.getPort() + " with length: " +
                        pack.getLength());

                    System.out.write(pack.getData(),0,pack.getLength());
                    //System.out.println("received msg: " + msg + "from " + pack.getAddress());
                    System.out.println();
                }
                catch(SocketTimeoutException e){
                    System.out.println("Timeout reached!!! " + e);
                    s.leaveGroup(InetAddress.getByName(group));
                    s.close();
                }

            }
        
        }
        catch (SocketException e){
            System.out.println("Socket closed " + e);
        }
    }
}
