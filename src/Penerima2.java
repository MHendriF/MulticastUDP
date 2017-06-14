import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Penerima2 {
    
    static Location iploc=new Location();
    
    public Location IPDetails(String ip){
        
        String[] parts, parts2, parts3;
        String key;
        URL url;
        int i = 0;
        String strTemp = "";
        String temporaray="";
        
        String temp[] = new String [150];
        try {
            
            key = "&auth=f95b478e-57cd-4a80-81c2-87436243ec4a";
            url = new URL("https://ipfind.co?ip="+ip+key);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            while (null != (strTemp = br.readLine())) {
                temporaray=strTemp;
            }
            
            //Get string in "string"
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(temporaray);
            
            i = 0;
            while (m.find()) {
              temp[i] = m.group(1);
              i++;
            }
            
            //parts = tes.split(",");
            parts = temporaray.split(",");
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
    
    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        //Address
        String multiCastAddress = "224.1.10.2";
        final int multiCastPort = 5268;
        final int bufferSize = 1024 * 4; //Maximum size of transfer object
        String my_ip, current_message;
        int NOWTTL, MAXTTL, NEXTID = 0;
        Date date1, date2;
        String _MessageOnly[] = new String [50];
        
        for(int i=0; i<_MessageOnly.length; i++){
            _MessageOnly[i] = null;  
        }
        
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
        
        ArrayList<Message> arr_msg=new ArrayList<Message>();  
        
        //Check IP Global
        URL url;
        try
        {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine(); //you get the IP as a String
            //System.out.println("My IP Public is : "+ip);
            Penerima2 findLocation = new Penerima2();
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
            //Get Current Time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            date1 = sdf.parse(st.currentTime());
            
            //Distance Location
            latitude1a = iploc.getLatitude();
            longitude1a = iploc.getLongitude();
            
            //Deserialze object
            ByteArrayInputStream in = new ByteArrayInputStream(buffer);
            ObjectInputStream is = new ObjectInputStream(in);
            try {
                Message message = (Message) is.readObject();
                MAXTTL = message.getMaxHop();
                NEXTID = message.getId();
                current_message = message.getMessage();
                
                for(int i=0; i<_MessageOnly.length; i++){
                    if(_MessageOnly[i] == null){
                        
                        date2 = sdf.parse(message.getTime());
                        LocationDistance loc1 = new LocationDistance("aaa", latitude1a, longitude1a);
                        LocationDistance loc2 = new LocationDistance("bbb", message.getLatitude(), message.getLongitude());
                        double distance = loc1.distanceTo(loc2);
                        System.out.println("Distance : " + distance + " miles");
                        if(distance > 7000){
                            System.out.println("Message could not be sent because of limited area...\n");
                            break;
                        }
                        
                        else if(date1.compareTo(date2) > 0){
                            System.out.println("Message could not be sent because message is expired...\n");
                            break;
                        }

                        else if(message.getHop() > MAXTTL){
                            System.out.println("Message could not be sent because hop is limited...\n");
                            break;
                        }
                        
                        else
                        {
                            _MessageOnly[i] = message.getMessage();
                            //add message object to array list
                            arr_msg.add(new Message(NEXTID, message.getMessage(), message.getSource(), 
                                message.getDestination(), message.getId(), message.getMaxHop(), message.getTime(), message.getLatitude(), message.getLongitude()));
                            break;
                        }
                        
                    }
                 else if(_MessageOnly[i] != null && _MessageOnly[i].equals(current_message)){
                       System.out.println("Message is already exist!");
                        
                        break;
                    }
                }
                
                Iterator itr=arr_msg.iterator(); 
                int a=0;
                //traversing elements of ArrayList object
                while(itr.hasNext()){
                    Message pesan=(Message)itr.next();
                    NOWTTL = pesan.getHop();
                    NEXTID = pesan.getId();
                    
                    date2 = sdf.parse(pesan.time);
                    LocationDistance loc1 = new LocationDistance("aaa", latitude1a, longitude1a);
                    LocationDistance loc2 = new LocationDistance("bbb", pesan.latitude, pesan.longitude);
                    double distance = loc1.distanceTo(loc2);
                    
                    //Check if distance so far
                    if(distance > 5000){
                        System.out.println("Message could not be sent because of limited area...\n");
                        arr_msg.remove(a);
                    }
                    //Check if message is expired
                    else if(date1.compareTo(date2) > 0){
                        System.out.println("Message could not be sent because message is expired...\n");
                        arr_msg.remove(a);
                    }

                    else if(message.getHop() > MAXTTL){
                        System.out.println("Message could not be sent because hop is limited...\n");
                        arr_msg.remove(a);
                        break;
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
                        System.out.println("Send Message : "+pesan);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream os = new ObjectOutputStream(baos);
                        
                        NOWTTL++;
                        NEXTID++;
                        pesan.setId(NEXTID);
                        pesan.setHop(NOWTTL);
                        
                        os.writeObject(pesan);
                        byte[] data = baos.toByteArray();
                        s.send(new DatagramPacket(data, data.length, group, multiCastPort));
                        Thread.sleep(1000);
                    }
                }  
            } 
            catch(SocketTimeoutException e){
                    System.out.println("Timeout reached!!! " + e);
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
 
        }
    }
}
