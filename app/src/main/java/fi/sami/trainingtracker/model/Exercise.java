package fi.sami.trainingtracker.model;

import java.util.Date;

/**
 * Created by Sami on 28.10.2015.
 */
public class Exercise {

    private Date date;
    private String participant;
    private Float hours;
    private Location location;

    public Exercise() {
    }

    public Exercise(Date date, String participant, Float hours, Location location) {
        this.setDate(date);
        this.setParticipant(participant);
        this.setHours(hours);
        this.setLocation(location);
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public Float getHours() {
        return hours;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "date=" + date +
                ", participant='" + participant + '\'' +
                ", hours=" + hours +
                ", location=" + location +
                '}';
    }
}
