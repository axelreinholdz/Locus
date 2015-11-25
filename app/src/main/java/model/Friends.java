package model;

/**
 * Created by Iris on 14/11/2015.
 */
public class Friends {
    private String userEmail;
    private String friendEmail;
    private String objectId;

    public Friends(String objectId, String userEmail, String friendEmail) {
        this.userEmail = userEmail;
        this.friendEmail = friendEmail;
        this.objectId = objectId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public String getObjectId(){return objectId;}
}
