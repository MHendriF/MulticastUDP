package Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetIpPublic {
    
    public IPInformation get_ip_Details(String ip) { 
        ip=ip.trim();
        int i =0;
        
	IPInformation ipinformation=new IPInformation();
        
	URL url;
	try {
            String key;
//            key = "&auth=f95b478e-57cd-4a80-81c2-87436243ec4a";
//            url = new URL("https://ipfind.co?ip="+ip+key);
//            
//            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            String temporaray="";
            String temp_array[]=null;
            String temp[] = new String [150];
            
            String tes = "{\"ip_address\":\"202.46.129.77\",\"country\":\"Indonesia\",\"country_code\":\"ID\",\"continent\":\"Asia\",\"continent_code\":\"AS\",\"city\":\"Surabaya\",\"county\":\"Kota Surabaya\",\"region\":\"East Java\",\"region_code\":\"08\",\"timezone\":\"Asia\\/Jakarta\",\"owner\":null,\"longitude\":112.7508,\"latitude\":-7.2492,\"currency\":\"IDR\",\"languages\":[\"id\",\"en\",\"nl\",\"jv\"]}";
            //String tes2 = 
//            while (null != (strTemp = br.readLine())) {
//                temporaray=strTemp;
//            }
            temporaray = tes;
            //System.out.println("Temporary "+temporaray);
						
            temp_array=temporaray.split(";");
            //System.out.println("Str length is "+temp_array.length );
            int length=temp_array.length;
						 
//            while(i<length){
//                System.out.println(i);
//                System.out.println(temp_array[i]);
//                i++;
//            }

            //System.out.println("Array 0 : "+temp_array[0] );

            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(temp_array[0]);

            i = 0;
            while (m.find()) {
              //System.out.println(m.group(1));
              temp[i] = m.group(1);
              i++;
            }
            
            String[] parts, parts2, parts3;
            parts = tes.split(",");
            
            //To get Longitude
            parts2 = parts[11].split(":");
            //System.out.println(parts2[1]);
            //To get Latitude
            parts3 = parts[12].split(":");
            //System.out.println(parts3[1]);
            
            ipinformation.setIP(temp[1]);
            ipinformation.setCountry(temp[3]);
            ipinformation.setCity(temp[11]);
            ipinformation.setLongitude(parts2[1]);
            ipinformation.setLatitude(parts3[1]);
            
            System.out.println("IP address : "+temp[1]);
            System.out.println("Country : "+temp[3]);
            System.out.println("City : "+temp[11]);
            System.out.println("Longitude : "+parts2[1]);
            System.out.println("Latitude : "+parts3[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
	return ipinformation;
    }
    
    public static void main(String[] args) throws UnknownHostException, MalformedURLException, IOException {
        String my_ip;
        my_ip = InetAddress.getLocalHost().getHostAddress();
        URL url;
        try
        {
            System.out.println("My IP is : " + my_ip);
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
            System.out.println(ip);
            
            GetIpPublic objGetLocation=new GetIpPublic();
            objGetLocation.get_ip_Details(ip);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }
}
