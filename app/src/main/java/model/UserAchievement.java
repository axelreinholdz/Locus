package model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Iris on 14/11/2015.
 */
public class UserAchievement {
    private int userId;
    private int achievementId;
    private Date date;
    private Time time;

    public UserAchievement(int userId, int achievementId, Date date, Time time) {
        this.userId = userId;
        this.achievementId = achievementId;
        this.date = date;
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
