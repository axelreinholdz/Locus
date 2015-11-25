package controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import model.Friends;
import model.GameRegistration;
import model.User;

/**
 * Created by Iris on 14/11/2015.
 */
public class UserManager {

        private User user;

        public boolean login(String email, String password, Context context){
            boolean isLogin = false;
            User user = getUserByEmail(email,context);
            if (user!=null && password.equalsIgnoreCase(user.getPassword())){
                isLogin = true;
            }
            return isLogin;
        }

        public User addUser(String email, String name, String password, String location) {
            User result = new User();
            try{
                JSONObject bigObj = new JSONObject();
                JSONArray propertyArray = new JSONArray();
                JSONObject smallObj = new JSONObject();
                smallObj.put("userId", name);

                propertyArray.put(smallObj);
                smallObj = new JSONObject();

                smallObj.put("name", name);
                propertyArray.put(smallObj);
                smallObj = new JSONObject();

                smallObj.put("email", email);
                propertyArray.put(smallObj);
                smallObj = new JSONObject();

                smallObj.put("password", password);
                propertyArray.put(smallObj);
                smallObj = new JSONObject();

                smallObj.put("location", location);
                propertyArray.put(smallObj);
                smallObj = new JSONObject();

                smallObj.put("last-location-lat", "0");
                propertyArray.put(smallObj);
                smallObj = new JSONObject();

                smallObj.put("last-location-long", "0");
                propertyArray.put(smallObj);
                smallObj = new JSONObject();

                bigObj.put("country","Singapore");
                bigObj.put("region","Singapore");
                bigObj.put("city","Singapore");
                bigObj.put("name",name);
                bigObj.put("objectTypeId","112");
                bigObj.put("userId","1");
                bigObj.put("appId","32");
                bigObj.put("properties", propertyArray);
                String json = bigObj.toString();
                HttpUtility httpUtility = new HttpUtility();
                String resultString = httpUtility.createObject(json);

                JSONObject resultJson = new JSONObject(resultString);
                result = convertJSONObjectToUser(resultJson);
            }catch(Exception e){
                e.printStackTrace();
            }

            return result;
        }

        public User getUserByEmail(String theEmail, Context context){

            User theUser = new User();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            HttpUtility httpUtility = new HttpUtility();
            String result = "";

            try {
                // wrap property to a JSON Array
                jsonObject.put("email", theEmail);
                jsonArray.put(jsonObject);

                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // pass property to get user back
                if(networkInfo != null && networkInfo.isConnected()){
                    result=httpUtility.getObjectByProperty("112",jsonArray.toString());
                }
                JSONArray userArray = new JSONArray(result);
                JSONObject obj = userArray.getJSONObject(0);

                // change JSONObject to Java Class
                theUser = convertJSONObjectToUser(obj);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return theUser;
        }

        public User getUserByObjectId (String ObjectId, Context context) {
            User theUser = new User();
            HttpUtility httpUtility = new HttpUtility();

            String url = "http://161.202.13.188:9000/api/object/get/"+ObjectId;
            try {
                String result = httpUtility.download(url);
                JSONObject obj = new JSONObject(result);
                theUser = convertJSONObjectToUser(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return theUser;
        }

        public ArrayList<User> getAllUsers(Context context){
            ArrayList<User> allUsers = new ArrayList<User>();

            String result = "";
            HttpUtility httpUtility = new HttpUtility();
            try{

                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    result = httpUtility.download("http://161.202.13.188:9000/api/object/get/app/32/objecttype/112");
                    Log.d("CONNECTION", "Connection made");
                }
                else {
                    result = "No network connection available";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONArray array = new JSONArray();

            try {
                array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    User user = convertJSONObjectToUser(obj);
                    allUsers.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return allUsers;
        }

        public boolean changePassword(String email, String oldPassword, String newPassword, Context context) {
            boolean result = false;
            User user = getUserByEmail(email, context);
            if (user != null && oldPassword.equalsIgnoreCase(user.getPassword())) {
                String objectId = user.getObjectId();
                JSONObject bigObj = new JSONObject();
                JSONObject smallObj = new JSONObject();
                JSONArray propertyArray = new JSONArray();
                try {
                    smallObj.put("password", newPassword);
                    propertyArray.put(smallObj);

                    bigObj.put("appId", "32");
                    bigObj.put("properties", propertyArray);
                    HttpUtility httpUtility = new HttpUtility();
                    String resultString = httpUtility.updateObjectByProperties(objectId, bigObj.toString());
                    System.out.println(resultString);
                    result = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    result = false;
                }


            }


            return result;

        }

            public ArrayList<User> getAllUsersSortedByShortestGameDuration (Context context){
                ArrayList<User> sortedUsers = new ArrayList<User>();
                GameRegistrationManager gameRegistrationManager = new GameRegistrationManager();
                ArrayList<GameRegistration> sortedGameRegistrationByDuration = gameRegistrationManager.getAllGameRegistrationSortedByShortestDuration(context);
                for (int i = 0; i < sortedGameRegistrationByDuration.size(); i++) {
                    String userObjectId = sortedGameRegistrationByDuration.get(i).getUserId();
                    long duration = sortedGameRegistrationByDuration.get(i).getDuration();
                    System.out.println(String.valueOf(duration));
                    User theUser = getUserByObjectId(userObjectId,context);
                    sortedUsers.add(theUser);
                }
                return sortedUsers;
            }

            public ArrayList<User> getAllFriendsSortedByShortestGameDuration (String userEmail, Context context){
                ArrayList<User> sortedFriends = new ArrayList<User>();
                GameRegistrationManager grm = new GameRegistrationManager();
                ArrayList<GameRegistration> friendgrList = new ArrayList<GameRegistration>();
                ArrayList<GameRegistration> sortedFriendgrList = new ArrayList<GameRegistration>();

                ArrayList<User> friends = getFriendsByUserEmail(userEmail,context);
                for (int i = 0; i < friends.size(); i++) {
                    User friend = friends.get(i);
                    GameRegistration friendgr = grm.getGameRegistrationByUserIdAndGameId(friend.getObjectId(),1,context);
                    friendgrList.add(friendgr);
                }

                // sort Friends' GR List
                Collections.sort(friendgrList);
                for (int i = 0; i < friendgrList.size(); i++) {
                    // gameRegistration must be completed, if duration is 0, the gameRegistration should not be ranked
                    if (friendgrList.get(i).getDuration()!=0) {
                        sortedFriendgrList.add(friendgrList.get(i));
                    }
                }

                for (int i = 0; i < sortedFriendgrList.size(); i++) {
                    String userObjectId = sortedFriendgrList.get(i).getUserId();
                    long duration = sortedFriendgrList.get(i).getDuration();
                    System.out.println(String.valueOf(duration));
                    User theUser = getUserByObjectId(userObjectId,context);
                    sortedFriends.add(theUser);
                }

                return sortedFriends;
            }

            public ArrayList<User> getFriendsByUserEmail (String theEmail, Context context) {
                ArrayList<User> friends = new ArrayList<User>();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                HttpUtility httpUtility = new HttpUtility();
                String result = "";

                try {
                    // wrap property to a JSON Array
                    jsonObject.put("userEmail", theEmail);
                    jsonArray.put(jsonObject);

                    ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                    if(networkInfo != null && networkInfo.isConnected()){
                        result = httpUtility.getObjectByProperty("115", jsonArray.toString());
                        Log.d("CONNECTION", "Connection made");
                    }
                    else {
                        result = "No network connection available";
                    }
                    Log.d("httpOutput",result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JSONArray array = new JSONArray();

                try {
                    array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Friends friendObject = convertJSONObjectToFriends(obj);
                        User friendUser = getUserByEmail(friendObject.getFriendEmail(), context);
                        friends.add(friendUser);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return friends;

            }

        private User convertJSONObjectToUser (JSONObject obj) throws JSONException {
            System.out.println();
            // change JSONObject to Java Class
            String objectId = obj.getString("id");
            String userId = obj.getString("userId");
            String name = obj.getString("name");
            String email = obj.getString("email");
            String password = obj.getString("password");
            String location = obj.getString("location");
            double last_location_lat = obj.getDouble("last-location-lat");
            double last_location_long = obj.getDouble("last-location-long");
            return new User(objectId,userId,name,email,password,location,last_location_lat,last_location_long);
        }

    private Friends convertJSONObjectToFriends (JSONObject obj) throws JSONException {
        String objectId = obj.getString("id");
        String userEmail = obj.getString("userEmail");
        String friendEmail = obj.getString("friendEmail");
        return new Friends(objectId, userEmail,friendEmail);
    }

        public void setLastLocation (String userEmail, double latitude, double longitude){
            // get user object
            // update user
        }
}
