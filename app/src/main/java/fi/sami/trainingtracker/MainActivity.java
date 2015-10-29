package fi.sami.trainingtracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import fi.sami.trainingtracker.component.ParticipantsFragment;

public class MainActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    private boolean dateSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new ParticipantsFragment()).commit();
        }

    }

    public void onStart() {
        super.onStart();

        Button addExerciseButton = (Button)findViewById(R.id.addNewExerciseButton);
        addExerciseButton.setEnabled(false);

        if(dateSet) {
            addExerciseButton.setEnabled(true);
        }
    }

    public void selectDate(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        // TODO: do something with the date
        Date date = calendar.getTime();

        String dateToDisplay = year + "-" + month + "-" + day;

        TextView textView = (TextView)findViewById(R.id.dateSetText);
        textView.setText("Date: " + dateToDisplay);

        dateSet = true;

        Button addExerciseButton = (Button)findViewById(R.id.addNewExerciseButton);
        addExerciseButton.setEnabled(true);

    }

    public void selectParticipants(View view) {

    }


    public void addNewExercise(View view) {
        Toast.makeText(getApplicationContext(), "Exercise added to database", Toast.LENGTH_SHORT).show();
    }


}
