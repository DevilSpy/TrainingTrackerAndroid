package fi.sami.trainingtracker.component;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import fi.sami.trainingtracker.MainActivity;
import fi.sami.trainingtracker.model.Exercise;

/**
 * Created by Sami on 28.10.2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), (MainActivity)getActivity(), year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

    }
}
