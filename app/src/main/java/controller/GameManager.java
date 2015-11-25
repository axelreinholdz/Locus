package controller;

import android.util.Log;

import org.json.JSONObject;

import model.Game;

/**
 * Created by axelreinholdz on 2015-11-17.
 */
public class GameManager {

    public GameManager() {
    }

    public Game getGameById(int GameNo) {
        String url = "";

        if (GameNo == 1) {
            url = "http://161.202.13.188:9000/api/object/get/1313";
        }

        Game newGame = new Game();
        String result = "";

        try {
            HttpUtility httpUtility = new HttpUtility();

            //parse String to JSONObject
            JSONObject obj = new JSONObject(result);

            String objectId = obj.getString("id");
            int GameId = obj.getInt("gameId");
            String Name = obj.getString("name");
            String Status = obj.getString("status");

            newGame = new Game(objectId, GameId, Name, Status);

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return newGame;

    }
}
