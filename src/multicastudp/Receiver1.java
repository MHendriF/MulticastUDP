package multicastudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import static multicastudp.MulticastUDPSender.group;

public class Receiver1 {
    final static int port = 5000;
    final static String group = "225.4.5.6";
    final static int ttl = 1;
//    Pesan _pesan = new Pesan();
    static Pesan _pesan = new Pesan();
    static String cek_pesan;
    static int idx = 0;
    static int hop = 0;
    static String my_ip;

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        try (MulticastSocket s = new MulticastSocket(port)){
            // join the multicast group
            s.joinGroup(InetAddress.getByName(group));
            byte buf[ ] = new byte[1024];
            my_ip = InetAddress.getLocalHost().getHostAddress();
            DatagramPacket pack = new DatagramPacket(buf, buf.length);
      
            while(true){
                s.receive(pack);
                String message = new String(buf, 0, buf.length);
                //Tampung pesan
                String[] words = message.split(":");
                //my_ip =  pack.getAddress().toString();
                System.out.println("MY IP: " +my_ip);
                
                //cek jika pesan untuk dia
                if(words[1] == null ? my_ip == null : words[1].equals(my_ip)){
                    //int number = Integer.parseInt(words[2]); 
//                    hop = ;
                    //System.out.println(number);
                    Integer[] intarray = new Integer[20];
                    intarray[1]=Integer.parseInt(words[2]); 
                    //String number = words[2];
                    //hop = Integer.valueOf("words[2]");
                    System.out.println("Pesan Sukses diterima");
                    System.out.println("Banyak Hop: " + intarray[1]);
                    System.out.println("Pesan diterima: " + message + " dari " + pack.getAddress()+":"+pack.getPort()+"\n");
                }
                
                else
                {
                    for(int i=0; i<Pesan._ListPesan.length; i++)
                    {
                        //cek_pesan = Pesan._ListPesan[i];
                        cek_pesan = _pesan.cekPesan(i); 
                        if(cek_pesan == null){
                            Pesan._ListPesan[i] = words[0];
                            System.out.println("Tampung pesan: " +words[0]);
                            //idx = i;
                            hop = hop + 1;
                            message = words[0]+":"+words[1]+":"+hop;
                            System.out.println("Pesan diterima: " + message + " dari " + pack.getAddress()+":"+pack.getPort()+"\n");
                            break;
                        }

                        //notif grup sudah ada
                        else if(cek_pesan.equals(words[0])){
                            hop = hop + 1;
                            message = words[0]+":"+words[1]+":"+hop;
                            System.out.println("Pesan sudah pernah diterima: "+ message);
                            //idx = i;
                            break;
                        }
                    }
                    
                    //Kirim ke yang lain
                    DatagramPacket msgPacket = new DatagramPacket(message.getBytes(),message.getBytes().length,InetAddress.getByName(group), port);
                    s.send(msgPacket,(byte)ttl);
                    Thread.sleep(5000);
                }

                
//                System.out.println("Received data from: " + pack.getAddress().toString() +
//		    ":" + pack.getPort() + " with length: " +
//		    pack.getLength());
                      
                //s.leaveGroup(InetAddress.getByName(group));
                //s.close();
                

            }
        
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
