package model;

/**
 * Created by Iris on 14/11/2015.
 */
public class Game {
    private String objectId;
    private int gameId;
    private String name;
    private String status;

    public Game() {
    }

    public Game(String objectId, int gameId, String name, String status) {
        this.objectId = objectId;
        this.gameId = gameId;
        this.name = name;
        this.status = status;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}



