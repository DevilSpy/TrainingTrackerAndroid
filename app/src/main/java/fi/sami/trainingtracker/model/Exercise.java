package fi.sami.trainingtracker.model;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by Sami on 28.10.2015.
 */
public class Exercise extends SugarRecord<Exercise>{

    private Date date;
    private Float hours;
    private Location location;
    private List<User> users;

    public Exercise() {
    }

    public Exercise(Date date, Float hours, Location location) {
        this.setDate(date);
        this.setHours(hours);
        this.setLocation(location);
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                ", hours=" + hours +
                ", location=" + location +
                '}';
    }
}
