import java.io.Serializable;

public class Message implements Serializable{
    public Message(int id, String msg, String source, String destination, int hop, String time, double latitude, double longitude) {
        this.id = id;
        this.msg = msg;
        this.source = source;
        this.destination = destination;
        this.hop = hop;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    Message(){
        for(int i=0; i<_ListMessage.length; i++){
            _ListMessage[i] = null;  
        }
    }
    private static final long serialVersionUID = 1L;
    public String _ListMessage[] = new String [50];
    private int id, hop;
    private double latitude, longitude;
    private String msg, source, destination, time;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
     public int getHop() {
        return hop;
    }

    public void setHop(int hop) {
        this.hop = hop;
    }

    public void addMessage(int i, String msg){
        this._ListMessage[i]= msg; 
    }

    public String checkMessage(int i){
        return _ListMessage[i];
    }
    
    public String getTime(){
        return time;
    }
    
    public double getLatitude(){
        return latitude;
    }
    
    public double getLongitude(){
        return longitude;
    }
    
    public String toString() {
        return "Id:" + getId() + " | Message: " + getMessage() + " | Source: " + getSource() 
                +" | Destination: " + getDestination() + " | Hop: "+getHop()
                +" | Expired: " + getTime() + " | Latitude: " + getLatitude()
                + " | Longitude: " + getLongitude();
    }
}
