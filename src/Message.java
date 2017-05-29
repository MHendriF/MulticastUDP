import java.io.Serializable;

public class Message implements Serializable{
    public Message(int id, String msg, String source, String destination, int hop, String time, String location) {
        this.id = id;
        this.msg = msg;
        this.source = source;
        this.destination = destination;
        this.hop = hop;
        this.time = time;
        this.location = location;
    }
    
    Message(){
        for(int i=0; i<_ListMessage.length; i++){
            _ListMessage[i] = null;  
        }
    }
    private static final long serialVersionUID = 1L;
    public String _ListMessage[] = new String [50];
    private int id;
    private String msg;
    private String source;
    private String destination;
    private int hop;
    private String time;
    private String location;
    
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
    
    public String getLocation(){
        return location;
    }
    
    public String toString() {
        return "Id:" + getId() + " | Message: " + getMessage() + " | Source: " + getSource() 
                +" | Destination: " + getDestination() + " | Hop: "+getHop()
                +" | Time: " + getTime() + " | Location: " + getLocation();
    }
}
