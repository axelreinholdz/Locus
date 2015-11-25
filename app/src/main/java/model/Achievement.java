package model;

/**
 * Created by Iris on 14/11/2015.
 */
public class Achievement {
    private int achievementId;
    private String name;
    private String type;
    private int points;

    public Achievement(int achievementId, String name, String type, int points) {
        this.achievementId = achievementId;
        this.name = name;
        this.type = type;
        this.points = points;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
