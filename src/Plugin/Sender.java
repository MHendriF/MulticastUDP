package Plugin;

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

public class Sender {

    public static void main(String[] args) throws IOException {
 
        //Address
        String multiCastAddress = "224.0.0.1";
        final int multiCastPort = 52684;
 
        //Create Socket
        System.out.println("Create socket on address " + multiCastAddress + " and port " + multiCastPort + ".");
        InetAddress group = InetAddress.getByName(multiCastAddress);
        MulticastSocket s = new MulticastSocket(multiCastPort);
        s.joinGroup(group);
 
        //Prepare Data
        String message = "Hello there!";
        //Message message = new Message(1, "Tes", "10.151.32.10", "11212",0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        byte[] data = baos.toByteArray();
 
        //Send data
        s.send(new DatagramPacket(data, data.length, group, multiCastPort));
    }
}
