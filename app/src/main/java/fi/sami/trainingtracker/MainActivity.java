package fi.sami.trainingtracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fi.sami.trainingtracker.component.DatePickerFragment;
import fi.sami.trainingtracker.component.SelectLocationDialogFragment;
import fi.sami.trainingtracker.component.SelectParticipantsDialogFragment;
import fi.sami.trainingtracker.model.Exercise;
import fi.sami.trainingtracker.model.Location;
import fi.sami.trainingtracker.model.User;
import fi.sami.trainingtracker.model.UserExercise;


public class MainActivity extends Activity implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener, SelectLocationDialogFragment.SelectLocationDialogFragmentListener, SelectParticipantsDialogFragment.SelectParticipantsDialogFragmentListener {

    private Float hours;
    private Date date;
    private String locationString;
    private List<String> participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Location> locations = Location.listAll(Location.class);
        if(locations == null || locations.isEmpty()) {
            Location location = new Location("Savela");
            Location location2 = new Location("Mattilanniemi");
            Location location3 = new Location("Viitaniemi");

            location.save();
            location2.save();
            location3.save();
        }
    }

    public void onStart() {
        super.onStart();

        showLatestExercise();
    }

    private void showLatestExercise() {
        Exercise exercise = Exercise.find(Exercise.class, null, null, null, "date DESC", "1").get(0);

        TextView textView = (TextView) findViewById(R.id.latestExercise);

        StringBuilder builder = new StringBuilder();

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

        textView.setText(builder.toString());

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

        date = calendar.getTime();

        String dateToDisplay = year + "-" + month + "-" + day;

        TextView selectedDateTextView = (TextView)findViewById(R.id.dateSetText);
        selectedDateTextView.setText("Date: " + dateToDisplay);

    }

    public void selectParticipants(View view) {
        SelectParticipantsDialogFragment fragment = new SelectParticipantsDialogFragment();
        fragment.show(getFragmentManager(), "participantsPicker");
    }

    @Override
    public void onReturnValue(List<String> participantsList) {
        participants = participantsList;
    }

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

                TextView selectedHours = (TextView)findViewById(R.id.hoursSetText);
                String hoursString = hoursArray.get(numberPicker.getValue());
                selectedHours.setText("Hours: " + hoursString);

                hours = Float.parseFloat(hoursString);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void selectLocation(View view) {
        SelectLocationDialogFragment fragment = new SelectLocationDialogFragment();
        fragment.show(getFragmentManager(), "locationPicker");
    }

    @Override
    public void onReturnValue(String locationFromDialog) {
        locationString = locationFromDialog;
    }


    public void addNewExercise(View view) {

        if (date == null || hours == null || locationString == null || participants == null) {
            Toast.makeText(getApplicationContext(), R.string.allMustBeSet, Toast.LENGTH_SHORT).show();
        } else if (!participants.isEmpty()) {

            Location location = Location.find(Location.class, "name = ?", locationString).get(0);
            List<User> users = new ArrayList<User>();

            for(String name : participants) {
                User user = User.find(User.class, "name = ?", name).get(0);
                users.add(user);
            }

            Exercise exercise = new Exercise();
            exercise.setDate(date);
            exercise.setLocation(location);
            exercise.setHours(hours);

            exercise.save();

            for(User user : users) {
                UserExercise userExercise = new UserExercise();
                userExercise.setUser(user);
                userExercise.setExercise(exercise);
                userExercise.save();
            }

            Toast.makeText(getApplicationContext(), "Exercise saved", Toast.LENGTH_SHORT).show();
        } else if(participants.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.participantsMustBeSet, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.somethingWentTerriblyWrong, Toast.LENGTH_SHORT).show();
        }

    }


    public void goToExercises(View view) {
        Intent intent = new Intent(MainActivity.this, ExercisesActivity.class);
        startActivity(intent);
    }


}
