package Plugin;

public class LocationDistance { 
    private String name;
    private double longitude;
    private double latitude;   
   
    // create and initialize a point with given name and
    // (latitude, longitude) specified in degrees
    public LocationDistance(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    // return distance between this location and that location
    // measured in statute miles
    public double distanceTo(LocationDistance that) {
        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(that.latitude);
        double lon2 = Math.toRadians(that.longitude);

        // great circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }

    // return string representation of this point
    public String toString() {
        return name + " (" + latitude + ", " + longitude + ")";
    }


    // test client
    public static void main(String[] args) {
        LocationDistance loc1 = new LocationDistance("PRINCETON_NJ", 40.366633, 74.640832);
        //LocationDistance loc2 = new LocationDistance("ITHACA_NY",    42.443087, 76.488707);
        LocationDistance loc2 = new LocationDistance("Surabaya",    -7.2492, 112.7508);
        double distance = loc1.distanceTo(loc2);
        //tdOut.printf("%6.3f miles from\n", distance);
        System.out.println(distance+" miles from " +loc1+" to "+loc2);
        //StdOut.println(loc1 + " to " + loc2);
    }
}