import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Penerima {
    
    static Location iploc=new Location();
    
    public Location IPDetails(String ip){
        
        String[] parts, parts2, parts3;
        String key;
        URL url;
        int i = 0;
        String strTemp = "";
        String temporaray="";
        String temp_array[]=null;
        String temp[] = new String [150];
        try {
            
//            key = "&auth=f95b478e-57cd-4a80-81c2-87436243ec4a";
//            url = new URL("https://ipfind.co?ip="+ip+key);
//            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//            while (null != (strTemp = br.readLine())) {
//                temporaray=strTemp;
//            }
            
            String tes = "{\"ip_address\":\"202.46.129.77\",\"country\":\"Indonesia\",\"country_code\":\"ID\",\"continent\":\"Asia\",\"continent_code\":\"AS\",\"city\":\"Surabaya\",\"county\":\"Kota Surabaya\",\"region\":\"East Java\",\"region_code\":\"08\",\"timezone\":\"Asia\\/Jakarta\",\"owner\":null,\"longitude\":112.7508,\"latitude\":-7.2492,\"currency\":\"IDR\",\"languages\":[\"id\",\"en\",\"nl\",\"jv\"]}";
            temporaray = tes;
            temp_array=temporaray.split(";");
            int length=temp_array.length;
            //Get string in "string"
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(temp_array[0]);

            i = 0;
            while (m.find()) {
              temp[i] = m.group(1);
              i++;
            }
            
            parts = tes.split(",");
            //parts = temporaray.split(",");
            //To get Longitude
            parts2 = parts[11].split(":");;
            //To get Latitude
            parts3 = parts[12].split(":");
            
            iploc.setIP(temp[1]);
            iploc.setCountry(temp[3]);
            iploc.setCity(temp[11]);
            iploc.setLongitude(parts2[1]);
            iploc.setLatitude(parts3[1]);
            
            System.out.println("IP address (Public) : "+temp[1]);
            System.out.println("Country : "+temp[3]);
            System.out.println("City : "+temp[11]);
            System.out.println("Longitude : "+parts2[1]);
            System.out.println("Latitude : "+parts3[1]);
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iploc;
    }
    
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
        
        //Check IP Global
        URL url;
        try
        {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine(); //you get the IP as a String
            //System.out.println("My IP Public is : "+ip);
            Penerima findLocation = new Penerima();
            findLocation.IPDetails(ip);

        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        double latitude1a, longitude1a, latitude1b, longitude1b;
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
            
            //Distance Location
            //System.out.println("Latitude" + iploc.getLatitude());
            latitude1a = Double.parseDouble(iploc.getLatitude());
            longitude1a = Double.parseDouble(iploc.getLongitude());
            
            //Deserialze object
            ByteArrayInputStream in = new ByteArrayInputStream(buffer);
            ObjectInputStream is = new ObjectInputStream(in);
            try {
                Message message = (Message) is.readObject();
                date2 = sdf.parse(message.getTime());
                
                LocationDistance loc1 = new LocationDistance("aaa", latitude1a, longitude1a);
                LocationDistance loc2 = new LocationDistance("bbb", message.getLatitude(), message.getLongitude());
                double distance = loc1.distanceTo(loc2);
                //System.out.println(distance+" miles from " +loc1+" to "+loc2);
                
                //Check if distance so far
                if(distance > 5000){
                    System.out.println("Message could not be sent because of limited area...\n");
                    s.leaveGroup(InetAddress.getByName(multiCastAddress));
                    s.joinGroup(group);
                }
                //Check if message is expired
                else if(date1.compareTo(date2) > 0){
                    System.out.println("Time limit access...\n");
                    s.leaveGroup(InetAddress.getByName(multiCastAddress));
                    s.joinGroup(group);
                }
                
                else if(message.getHop() > MAXTTL){
                    System.out.println("Send packet is limited...\n");
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
                            message.getDestination(), nexthop, message.getTime(), message.getLatitude(), message.getLongitude());
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
