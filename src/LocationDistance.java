public class LocationDistance {
    private String name;
    private double longitude2;
    private double latitude2;   
   
    // create and initialize a point with given name and
    // (latitude, longitude) specified in degrees
    public LocationDistance(String name, double latitude2, double longitude2) {
        this.name = name;
        this.latitude2  = latitude2;
        this.longitude2 = longitude2;
    }

    // return distance between this location and that location
    // measured in statute miles
    public double distanceTo(LocationDistance that) {
        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        double lat1 = Math.toRadians(this.latitude2);
        double lon1 = Math.toRadians(this.longitude2);
        double lat2 = Math.toRadians(that.latitude2);
        double lon2 = Math.toRadians(that.longitude2);

        // great circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        
        //convert mile to meter
        //statuteMiles = statuteMiles * 1609.34;
                
        return statuteMiles;
    }

    // return string representation of this point
    public String toString() {
        return name + " (" + latitude2 + ", " + longitude2 + ")";
    }
    
    public static void main(String[] args) {
//        LocationDistance loc1 = new LocationDistance("PRINCETON_NJ", 40.366633, 74.640832);
//        LocationDistance loc2 = new LocationDistance("ITHACA_NY",    42.443087, 76.488707);  
//        double distance = loc1.distanceTo(loc2);
//        System.out.println(distance+" miles from " +loc1+" to "+loc2);
    }

}
