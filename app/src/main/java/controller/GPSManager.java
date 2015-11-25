package controller;

import android.content.Context;

import model.User;

/**
 * Created by axelreinholdz on 2015-11-17.
 */
public class GPSManager {
    private double currentLatitude;
    private double currentLongitude;

    public boolean isAtRightLocation (Context context, double Latitude, double Longitude, String userEmail){
        boolean isAtRightLocation = false;

        boolean canGetLocation = getCurrentLocation(context);

        if (canGetLocation){
            // set location to the user's "last location"
            UserManager userManager = new UserManager();
            userManager.setLastLocation(userEmail,currentLatitude,currentLongitude);
        }

        if (    (Latitude-0.001) < currentLatitude
                && currentLatitude < (Latitude+0.001)
                && (Longitude-0.001)<currentLongitude
                && currentLongitude<(Longitude+0.001)   ){
            isAtRightLocation = true;
        }
        return isAtRightLocation;
    }

    private boolean getCurrentLocation(Context context){
        GPSTracker gps = new GPSTracker(context);
        if (gps.canGetLocation()) {
            currentLatitude = gps.getLatitude();
            currentLongitude = gps.getLongitude();
            return true;
        } else {
            gps.showSettingsAlert();
            return false;
        }
    }

    public double getCurrentLatitude(Context context){
        GPSTracker gps = new GPSTracker(context);
        if (gps.canGetLocation()) {
            double currentLatitude = gps.getLatitude();
            return currentLatitude;
        } else {
            gps.showSettingsAlert();
            return 0;
        }
    }

    public double getCurrentLongitude(Context context){
        GPSTracker gps = new GPSTracker(context);
        if (gps.canGetLocation()) {
            double currentLongitude = gps.getLongitude();
            return currentLongitude;
        } else {
            gps.showSettingsAlert();
            return 0;
        }
    }

    public double calculateDistance(User user1, User user2) {

        double lat1 = user1.getLast_location_lat();
        double long1 = user1.getLast_location_long();
        double lat2 = user2.getLast_location_lat();
        double long2 = user2.getLast_location_long();

        // convert latitude and longitude decimal degrees to meters
        lat1 = convertDecimalDegreeToMeter(lat1);
        long1 = convertDecimalDegreeToMeter(long1);
        lat2 = convertDecimalDegreeToMeter(lat2);
        long2 = convertDecimalDegreeToMeter(long2);

        return Math.sqrt((lat2-lat1)*(lat2-lat1)+(long2-long1)*(long2-long1));
    }

    private double convertDecimalDegreeToMeter(double decimalDegree){
        // 1 decimal degree = 111320 meters
        return decimalDegree*111320;
    }
}
