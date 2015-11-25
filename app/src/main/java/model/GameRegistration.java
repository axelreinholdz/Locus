package model;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Iris on 14/11/2015.
 */
public class GameRegistration implements Comparable<GameRegistration>{
    private String objectId;
    private String userId;
    private int gameId;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private int currentQuestionNo;
    private long duration;

    public GameRegistration() {
    }

    public GameRegistration(String userId, int gameId, Date startDate, Time startTime) {
        this.userId = userId;
        this.gameId = gameId;
        this.startDate = startDate;
        this.startTime = startTime;
        this.currentQuestionNo = 1;
    }

    public GameRegistration(String objectId, String userId, int gameId, Date startDate, Time startTime, Date endDate, Time endTime, int currentQuestionNo, long duration) {
        this.objectId = objectId;
        this.userId = userId;
        this.gameId = gameId;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.currentQuestionNo = currentQuestionNo;
        this.duration = duration;
    }

    public long calculateDuration () {
        if (endTime!=null && endDate!=null){
            try{
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                DateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startDateStr = dateFormat.format(startDate);
                String startTimeStr = timeFormat.format(startTime);
                String endDateStr = dateFormat.format(endDate);
                String endTimeStr = timeFormat.format(endTime);
                java.util.Date parsedStartDate = fullFormat.parse(startDateStr+" "+startTimeStr);
                java.util.Date parsedEndDate = fullFormat.parse(endDateStr+" "+endTimeStr);
                return parsedEndDate.getTime()-parsedStartDate.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getCurrentQuestionNo() {
        return currentQuestionNo;
    }

    public void setCurrentQuestionNo(int currentQuestionNo) {
        this.currentQuestionNo = currentQuestionNo;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public int compareTo(GameRegistration compareGameRegistration) {
        // ascending duration (shorter durations first)
        return (int) (this.duration-compareGameRegistration.getDuration());
    }
    
}
