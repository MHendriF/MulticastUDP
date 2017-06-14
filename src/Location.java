public class Location {
   private String ip_address;
    private String country;
    private String city;
    private String longitude;
    private String latitude;
    double bujur;
    double lintang;

    public void setIP(String ip) {
	this.ip_address = ip;
    }
    
    public String getIP() {
        return ip_address;
    }
    
    public void setCountry(String country) {
	this.country = country;
    }
    
    public String getCountry() {
        return country;
    }
    
    
    public void setCity(String city) {
	this.city = city;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setLongitude(String longitude) {
	this.longitude = longitude;
    }
    
    public double getLongitude() {
        bujur = Double.valueOf(longitude);
        return bujur;
    }
    
    public void setLatitude(String latitude) {
	this.latitude = latitude;
    }
    
    public double getLatitude() {
        lintang = Double.parseDouble(latitude);
        return lintang;
    }
}
