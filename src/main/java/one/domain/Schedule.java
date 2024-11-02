package one.domain;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
    private String userID;
    private String scheduleID;
    private String scheduleTitle;
    private String scheduleDetail;
    private String startTime;
    private String endTime;
    private int isAlert;

    public int getIsOk() {
        return isOk;
    }

    public void setIsOk(int isOk) {
        this.isOk = isOk;
    }

    private int isOk;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public String getScheduleDetail() {
        return scheduleDetail;
    }

    public void setScheduleDetail(String scheduleDetail) {
        this.scheduleDetail = scheduleDetail;
    }

    public String getStartTime() {
        Date time = null;
        try {
            time=df.parse(startTime);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,"开始时间格式有误，请重新填写");
        }
        return df.format(time);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        if(endTime==null||endTime.equals("")){
            return endTime;
        }
        Date time = null;
        try {
            time=df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(time);
    }

    public void setEndTime(String endTime) {
        
        this.endTime = endTime;
    }

    public int getIsAlert() {
        return isAlert;
    }

    public void setIsAlert(int isAlert) {
        this.isAlert = isAlert;
    }

    @Override
    public String toString() {
        if(this.getEndTime()==null||this.getEndTime().equals("")){
            this.setEndTime("无");
        }
        return  "日程标题：" + scheduleTitle+"\n"+
                "日程详情：" + scheduleDetail+"\n"+
                "日程开始时间：" + startTime+"\n"+
                "日程结束时间：" + endTime;
    }
}
