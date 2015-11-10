package fi.sami.trainingtracker.component;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fi.sami.trainingtracker.DatabaseOpenHelper;
import fi.sami.trainingtracker.MainActivity;
import fi.sami.trainingtracker.R;

/**
 * Created by Sami on 5.11.2015.
 */
public class SelectLocationDialogFragment extends DialogFragment {
    int mSelectedItem;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.list_dialogfragment, null, false);

        final String[] locations = getLocations();

        builder.setView(dialogView)
                .setTitle("Select location")
                .setSingleChoiceItems(locations, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mSelectedItem = id;
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TextView selectedLocationTextView = (TextView) getActivity().findViewById(R.id.locationSetText);
                        List<String> locationsArray = Arrays.asList(locations);

                        String location = locationsArray.get((int)mSelectedItem);

                        selectedLocationTextView.setText("Location: " + location);

                        SelectLocationDialogFragmentListener activity = (SelectLocationDialogFragmentListener) getActivity();
                        activity.onReturnValue(location);
                    }
                })
                .setNegativeButton(R.string.cancel,  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        return builder.create();
    }

    private String[] getLocations() {
        List<String> locations = new ArrayList<String>();

        SQLiteDatabase db = (new DatabaseOpenHelper(getContext())).getWritableDatabase();
        String query = "SELECT name FROM location;";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            locations.add(cursor.getString(0));
            while (cursor.moveToNext()) {
                locations.add(cursor.getString(0));
            }
        }

        cursor.close();
        db.close();

        String[] locationsStringArray = locations.toArray(new String[locations.size()]);
        return locationsStringArray;
    }

    public interface SelectLocationDialogFragmentListener {
        public void onReturnValue(String location);
    }
}
