package controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.Collections;

import model.GameRegistration;

/**
 * Created by axelreinholdz on 2015-11-17.
 */
public class GameRegistrationManager {

    private GameRegistration gameRegistration;

    public GameRegistrationManager() {
    }


    public GameRegistration getGameRegistrationByObjectId(String objectId){

        GameRegistration gameRegistration = new GameRegistration();
        HttpUtility httpUtility = new HttpUtility();
        String url = "http://161.202.13.188:9000/api/object/get/"+objectId;

        try {
            String result = httpUtility.download(url);
            JSONObject obj = new JSONObject(result);
            gameRegistration = convertJSONObjectToGameRegistration(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameRegistration;

    }


    public GameRegistration getGameRegistrationByUserIdAndGameId(String userId, int gameId, Context context){

        GameRegistration gameRegistration = new GameRegistration();
        JSONArray jsonArray = new JSONArray();
        HttpUtility httpUtility = new HttpUtility();
        String result = "";

        // wrap properties to a JSON Array
        try {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("userId", userId);
            jsonArray.put(jsonObject1);
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("gameId", gameId);
            jsonArray.put(jsonObject2);

            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // pass property to get gameRegistration back
            if(networkInfo != null && networkInfo.isConnected()){
                result=httpUtility.getObjectByProperty("111",jsonArray.toString());
            }
            Log.d("httpOutput", result);
            JSONArray gameRegistrationArray = new JSONArray(result);
            JSONObject obj = gameRegistrationArray.getJSONObject(0);

            gameRegistration = convertJSONObjectToGameRegistration(obj);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return gameRegistration;
    }


    public GameRegistration createGameRegistration(String userId){
        //MUST READ: userId is the objectID of that user object, gameID is the objectID of that game object
        GameRegistration gameRegistration = new GameRegistration();
        Calendar cal = Calendar.getInstance();
        DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String startDate = dayFormat.format(cal.getTime());
        String startTime = hourFormat.format(cal.getTime());
        String endDate = "";
        String endTime = "";
        String currentQuestionNo = "1";
        String duration = "0";

        JSONObject bigObj = new JSONObject();
        try{
            bigObj.put("userId", userId);
            bigObj.put("gameId", "1313");
            bigObj.put("startDate", startDate);
            bigObj.put("startTime", startTime);
            bigObj.put("endDate", endDate);
            bigObj.put("endTime", endTime);
            bigObj.put("currentQuestionNo", currentQuestionNo);
            bigObj.put("duration", duration);

            HttpUtility httpUtility = new HttpUtility();
            JSONArray propertyArray = httpUtility.convertJSONObjectToJSONArray(bigObj);

            bigObj = new JSONObject();
            bigObj.put("country","Singapore");
            bigObj.put("region","Singapore");
            bigObj.put("city","Singapore");
            bigObj.put("name", userId+"1313");
            bigObj.put("objectTypeId","111");
            bigObj.put("userId","1");
            bigObj.put("appId","32");
            bigObj.put("properties", propertyArray);
            String resultString = httpUtility.createObject(bigObj.toString());
            JSONObject resultJson = new JSONObject(resultString);
            gameRegistration = convertJSONObjectToGameRegistration(resultJson);
            System.out.println("OBJECT ID IS "+gameRegistration.getObjectId());
        } catch (Exception e){
            e.printStackTrace();
        }

        return gameRegistration;
    }

    public GameRegistration updateCurrentQuestionNo(String objectId, String currentQuestionNo){
        GameRegistration gr = new GameRegistration();
        int questionNo = Integer.parseInt(currentQuestionNo);
        questionNo +=1; //new Question No
        currentQuestionNo = String.valueOf(questionNo);

        JSONObject bigObj = new JSONObject();
        try{
            bigObj.put("currentQuestionNo",currentQuestionNo);
            HttpUtility httpUtility = new HttpUtility();
            JSONArray propertyArray = httpUtility.convertJSONObjectToJSONArray(bigObj);
            bigObj = new JSONObject();
            bigObj.put("appId","32");
            bigObj.put("properties", propertyArray);
            String resultString = httpUtility.updateObjectByProperties(objectId, bigObj.toString());
            gr = convertJSONObjectToGameRegistration(new JSONObject(resultString));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return gr;
    }

    public GameRegistration updateEndDateTime (String objectId) {

        GameRegistration gr = new GameRegistration();
        Calendar cal = Calendar.getInstance();
        DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String endDate = dayFormat.format(cal.getTime());
        String endTime = hourFormat.format(cal.getTime());



        JSONObject bigObj = new JSONObject();
        try {
            //update endDate and endTime
            bigObj.put("endDate", endDate);
            bigObj.put("endTime", endTime);
            HttpUtility httpUtility = new HttpUtility();
            JSONArray propertyArray = httpUtility.convertJSONObjectToJSONArray(bigObj);
            bigObj = new JSONObject();
            bigObj.put("appId", "32");
            bigObj.put("properties", propertyArray);
            String resultString = httpUtility.updateObjectByProperties(objectId, bigObj.toString());
            gr = convertJSONObjectToGameRegistration(new JSONObject(resultString));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return gr;

    }


    public GameRegistration updateDuration (GameRegistration gr) {


        JSONObject bigObj = new JSONObject();
        JSONArray propertyArray = new JSONArray();
        try {
            HttpUtility httpUtility = new HttpUtility();
            String duration = String.valueOf(gr.calculateDuration());

            bigObj.put("duration", duration);
            propertyArray = httpUtility.convertJSONObjectToJSONArray(bigObj);
            bigObj = new JSONObject();
            bigObj.put("appId", "32");
            bigObj.put("properties", propertyArray);
            String resultString = httpUtility.updateObjectByProperties(gr.getObjectId(), bigObj.toString());
            gr = convertJSONObjectToGameRegistration(new JSONObject(resultString));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gr;

    }

        public ArrayList<GameRegistration> getAllGameRegistrationSortedByShortestDuration (Context context){
            ArrayList<GameRegistration> sortedGameRegistration = new ArrayList<GameRegistration>();
            ArrayList<GameRegistration> allGameRegistration = getAllGameRegistration(context);
            Collections.sort(allGameRegistration);
            for (int i = 0; i < allGameRegistration.size(); i++) {
                // gameRegistration must be completed, if duration is 0, the gameRegistration should not be ranked
                if (allGameRegistration.get(i).getDuration()!=0) {
                    sortedGameRegistration.add(allGameRegistration.get(i));
                }
            }
            return sortedGameRegistration;
        }

        public ArrayList<GameRegistration> getAllGameRegistration (Context context) {
            ArrayList<GameRegistration> allGameRegistrations = new ArrayList<GameRegistration>();

            String result = "";
            HttpUtility httpUtility = new HttpUtility();
            try{

                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    result = httpUtility.download("http://161.202.13.188:9000/api/object/get/app/32/objecttype/111");
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
                    GameRegistration gameRegistration = convertJSONObjectToGameRegistration(obj);
                    allGameRegistrations.add(gameRegistration);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return allGameRegistrations;
        }

    private GameRegistration convertJSONObjectToGameRegistration(JSONObject obj) throws JSONException, ParseException {
        String objectId = obj.getString("id");
        String userId = obj.getString("userId");
        int gameId = obj.getInt("gameId");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = null;
        parsedDate = dateFormat.parse(obj.getString("startDate"));
        Date startDate = new java.sql.Date(parsedDate.getTime());
        Time startTime = Time.valueOf(obj.getString("startTime"));
        String dateString = obj.getString("endDate");
        Date endDate = null;
        if (!dateString.isEmpty()){
            parsedDate = dateFormat.parse(dateString);
            endDate = new java.sql.Date(parsedDate.getTime());
        }
        Time endTime = null;
        String timeString = obj.getString("endTime");
        if (!timeString.isEmpty()){
            endTime = Time.valueOf(timeString);
        }
        int currentQuestionNo = obj.getInt("currentQuestionNo");
        long duration = obj.getLong("duration");
        return new GameRegistration(objectId,userId,gameId,startDate,startTime,endDate,endTime,currentQuestionNo,duration);
    }

    public String printDuration (long duration){
        long durationInHours = duration / (60 * 60 * 1000);
        long durationInMinutes = (duration - durationInHours * (60 * 60 * 1000)) / (60 * 1000);
        long durationInSeconds = (duration - durationInHours * (60 * 60 * 1000) - durationInMinutes * (60 * 1000)) / 1000;
        return String.valueOf(durationInHours)+"h "+String.valueOf(durationInMinutes)+"m "+String.valueOf(durationInSeconds)+"s";
    }
}
