package model;

/**
 * Created by Iris on 14/11/2015.
 */
public class User {
    private String objectId;
    private String userId;
    private String name;
    private String email;
    private String password;
    private String location;
    private double last_location_lat;
    private double last_location_long;

    public User() {
    }

    public User(String objectId, String userId, String name, String email, String password, String location, double last_location_lat, double last_location_long) {
        this.objectId = objectId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.last_location_lat = last_location_lat;
        this.last_location_long = last_location_long;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLast_location_lat() {
        return last_location_lat;
    }

    public void setLast_location_lat(double last_location_lat) {
        this.last_location_lat = last_location_lat;
    }

    public double getLast_location_long() {
        return last_location_long;
    }

    public void setLast_location_long(double last_location_long) {
        this.last_location_long = last_location_long;
    }

}
