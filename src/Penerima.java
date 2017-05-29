import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.Date;
import java.util.TimerTask;

public class Penerima {
    public static void main(String[] args) throws IOException, ParseException {
        //Address
        String multiCastAddress = "224.0.0.1";
        final int multiCastPort = 52684;
        final int bufferSize = 1024 * 4; //Maximum size of transfer object
        String my_ip;
        int nexthop, nextid=0;
        final int MAXTTL = 1000;
        Date date1, date2;
        
        //Get ip address
        my_ip = InetAddress.getLocalHost().getHostAddress();
        //Message _pesan = new Message();
 
        //Create Socket
        System.out.println("Create socket on address " + multiCastAddress + " and port " + multiCastPort + ".");
        System.out.println("My IP is : " + my_ip);
        
        InetAddress group = InetAddress.getByName(multiCastAddress);
        MulticastSocket s = new MulticastSocket(multiCastPort);
        s.joinGroup(group);
        
        //Time Schedule
        Timer time = new Timer(); // Instantiate Timer Object
	ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
	time.schedule(st, 0, 1000); // Create Repetitively task for every 1 secs
        
        //Receive data
        while (true) {
            System.out.println("Wating for datagram to be received...");
 
            //Create buffer
            byte[] buffer = new byte[bufferSize];
            s.receive(new DatagramPacket(buffer, bufferSize, group, multiCastPort));
            System.out.println("Datagram received!");
            
            //Get Current Time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            date1 = sdf.parse(st.currentTime());
            
            //Deserialze object
            ByteArrayInputStream in = new ByteArrayInputStream(buffer);
            ObjectInputStream is = new ObjectInputStream(in);
            try {
                Message message = (Message) is.readObject();
                date2 = sdf.parse(message.getTime());
                
                //Check if message is expired
                if(date1.compareTo(date2) > 0){
                    System.out.println("Time limit access...");
                    s.leaveGroup(InetAddress.getByName(multiCastAddress));
                    s.joinGroup(group);
                }
                
                else if(message.getHop() > MAXTTL){
                    System.out.println("Send packet is limited...");
                    s.leaveGroup(InetAddress.getByName(multiCastAddress));
                    s.joinGroup(group);
                    //break;
                }
                
                else if(my_ip.equals(message.getDestination())){
                    System.out.println("Pesan sampai");
                    System.out.println("Message object received : "+message);
                    System.out.println("ID : "+message.getId());
                    System.out.println("Message : "+message.getMessage());
                    System.out.println("Source : "+message.getSource());
                    System.out.println("Destination : "+message.getDestination());
                    System.out.println("Hop : "+message.getHop());
                }
                else{
                    for(int i=0; i<message._ListMessage.length; i++){
                        if(message.checkMessage(i) == null){
                            message.addMessage(i, message.getMessage());
                            break;
                        }
                        else if(!message.checkMessage(i).equals(message.getMessage())){
                            message.addMessage(i, message.getMessage());
                            break;
                        } 
                        else if(message.checkMessage(i).equals(message.getMessage())){
                            System.out.println("Pesan sudah pernah diterima");
                            break;
                        }
                    }
                    //Increament hop
                    nexthop = message.getHop() + 1;
                    nextid = message.getId() + 1;
                    message.setHop(nexthop);
                    
                    //Send message again
                    Message message2 = new Message(nextid, message.getMessage(), message.getSource(), 
                            message.getDestination(), nexthop, message.getTime(), message.getLocation());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(baos);
                    os.writeObject(message2);
                    byte[] data = baos.toByteArray();
                    s.send(new DatagramPacket(data, data.length, group, multiCastPort));
                    Thread.sleep(2000);
                    System.out.println("Message object received: "+message);
                }
                
            } 
            catch(SocketTimeoutException e){
                    System.out.println("Timeout reached!!! " + e);
            }
            catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
 
        }
    }
}
