package fi.sami.trainingtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fi.sami.trainingtracker.model.Exercise;
import fi.sami.trainingtracker.model.User;
import fi.sami.trainingtracker.model.UserExercise;

public class ExercisesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
    }

    public void onStart() {
        super.onStart();

        listExercises();
    }

    private void listExercises() {
        List<Exercise> exercises = Exercise.find(Exercise.class, null, null, null, "date DESC", null);

        Toast.makeText(getApplicationContext(), "Found " + exercises.size() + " exercises", Toast.LENGTH_SHORT).show();

        TextView textView = (TextView) findViewById(R.id.exercisesList);
        StringBuilder builder = new StringBuilder();

        for(Exercise exercise : exercises) {

            Date date = exercise.getDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            builder.append(calendar.get(Calendar.DATE));
            builder.append("-");
            builder.append(calendar.get(Calendar.MONTH) + 1);
            builder.append("-");
            builder.append(calendar.get(Calendar.YEAR));

            builder.append("\n");

            List<UserExercise> userExercises = UserExercise.find(UserExercise.class, "exercise = ?", exercise.getId().toString());

            for(int i=0; i<userExercises.size(); i++) {
                builder.append(userExercises.get(i).getUser().getName());

                if(i != userExercises.size() - 1) {
                    builder.append(", ");
                }
            }

            builder.append("\n");
            builder.append(exercise.getHours() + "h");
            builder.append("\n");
            builder.append(exercise.getLocation().getName());
            builder.append("\n");
            builder.append("\n");
        }

        textView.setText(builder.toString());

    }

    public void goToMain(View view) {
        Intent intent = new Intent(ExercisesActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
