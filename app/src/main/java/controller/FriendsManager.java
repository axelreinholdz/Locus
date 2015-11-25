package controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Friends;

/**
 * Created by axelreinholdz on 2015-11-19.
 */
public class FriendsManager {

    private Friends friends;

    public Friends addFriends(String userEmail, String friendEmail){
        Friends f = null;
        JSONObject bigObj = new JSONObject();
        JSONArray propertyArray = new JSONArray();
        try{
            bigObj.put("userEmail", userEmail);
            bigObj.put("friendEmail", friendEmail);
            HttpUtility httpUtility = new HttpUtility();
            propertyArray = httpUtility.convertJSONObjectToJSONArray(bigObj);
            bigObj = new JSONObject();
            bigObj.put("country","Singapore");
            bigObj.put("region","Singapore");
            bigObj.put("city","Singapore");
            bigObj.put("name",userEmail+friendEmail);
            bigObj.put("objectTypeId","115");
            bigObj.put("userId","1");
            bigObj.put("appId","32");
            bigObj.put("properties", propertyArray);
            String resultString = httpUtility.createObject(bigObj.toString());
            f = convertJSONObjectToFriends(new JSONObject(resultString));
        } catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }

    private Friends convertJSONObjectToFriends(JSONObject obj) throws JSONException {
        String objectId = obj.getString("id");
        String userEmail = obj.getString("userEmail");
        String friendEmail = obj.getString("friendEmail");
        return new Friends(objectId, userEmail, friendEmail);
    }

}
