package Plugin;


public class IPInformation {
    private String ip_address;
    private String country;
    private String city;
    private String longitude;
    private String latitude;
    
    private String continent;
    private String region;
    
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
    
    public void setContinent(String continent) {
	this.continent = continent;
    }
    
    public String getContinent() {
        return continent;
    }
    
    public void setCity(String city) {
	this.city = city;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setRegion(String region) {
	this.region = region;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setLongitude(String longitude) {
	this.longitude = longitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLatitude(String latitude) {
	this.latitude = latitude;
    }
    
    public String getLatitude() {
        return latitude;
    }

}
