package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MonitoredData {
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String activity_name;

    public MonitoredData(){

    }

    public MonitoredData(LocalDateTime start_time, LocalDateTime end_time, String activity_name) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.activity_name = activity_name;
    }

    public String getActivityName(){
        return activity_name;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public int getDay(int first_day){
        return this.getStart_time().getDayOfYear()-first_day+1;
    }

    public int activityDuration(){
        return (int) Math.abs(Duration.between(this.getStart_time(),this.getEnd_time()).toSeconds());
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return start_time.format(formatter) + "  " + end_time.format(formatter) + "  " + activity_name.toString() ;
    }

    public MonitoredData convertString (String s){
        s.trim().replaceAll("\\s+", "");
        String[] words=s.split("\\s+");

        String date_time_start = words[0]+" "+words[1];
        String date_time_end = words[2]+" "+words[3];

        String activity = words[4];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(date_time_start, formatter);
        LocalDateTime end = LocalDateTime.parse(date_time_end, formatter);
        MonitoredData data = new MonitoredData(start, end, activity);

        return data;
    }

}
