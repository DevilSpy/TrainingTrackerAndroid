package fi.sami.trainingtracker.model;

import com.orm.SugarRecord;

/**
 * Created by Sami on 28.11.2015.
 */
public class UserExercise extends SugarRecord<UserExercise> {

    private Exercise exercise;
    private User user;

    public UserExercise() {

    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
