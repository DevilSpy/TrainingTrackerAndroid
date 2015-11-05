package fi.sami.trainingtracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fi.sami.trainingtracker.component.DatePickerFragment;
import fi.sami.trainingtracker.component.SelectLocationDialogFragment;
import fi.sami.trainingtracker.component.SelectParticipantsDialogFragment;


public class MainActivity extends Activity implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener {

    private boolean dateSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        TextView selectedDateTextView = (TextView)findViewById(R.id.dateSetText);
        selectedDateTextView.setText("Date: " + dateToDisplay);

        dateSet = true;

        Button addExerciseButton = (Button)findViewById(R.id.addNewExerciseButton);
        addExerciseButton.setEnabled(true);

    }

    public void selectParticipants(View view) {
        SelectParticipantsDialogFragment fragment = new SelectParticipantsDialogFragment();
        fragment.show(getFragmentManager(), "participantsPicker");
    }

    // TODO: numberPicker crashes for some reason when reached maxValue
    public void selectHours(View view) {
        // show NumberPicker
        show();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {

    }

    // show NumberPicker
    public void show() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Select hours");
        dialog.setContentView(R.layout.numberpicker);

        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        Button setButton = (Button) dialog.findViewById(R.id.setButton);

        final String[] displayValues = {"0", "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5"};

        final NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(displayValues.length - 1);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(this);
        numberPicker.setDisplayedValues(displayValues);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> hoursArray = Arrays.asList(displayValues);

                // TODO: do something with hours value
                TextView selectedHours = (TextView)findViewById(R.id.hoursSetText);
                selectedHours.setText("Hours: " + hoursArray.get(numberPicker.getValue()));
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void selectLocation(View view) {
        SelectLocationDialogFragment fragment = new SelectLocationDialogFragment();
        fragment.show(getFragmentManager(), "locationPicker");
    }


    public void addNewExercise(View view) {
        Toast.makeText(getApplicationContext(), "Exercise added to database", Toast.LENGTH_SHORT).show();
    }


}
