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
import fi.sami.trainingtracker.R;

/**
 * Created by Sami on 30.10.2015.
 */
public class SelectParticipantsDialogFragment extends DialogFragment {
    List mSelectedItems = new ArrayList();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.list_dialogfragment, null, false);

        final String[] participants = getParticipants();

        builder.setView(dialogView)
               .setTitle(R.string.selectParticipants)
                .setMultiChoiceItems(participants, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(id);
                        } else if (mSelectedItems.contains(id)) {
                            mSelectedItems.remove(Integer.valueOf(id));
                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    // TODO: do something with selected participants
                    public void onClick(DialogInterface dialog, int id) {
                        TextView selectedParticipantsTextView = (TextView) getActivity().findViewById(R.id.participantsSetText);

                        List<String> participantsArray = Arrays.asList(participants);

                        StringBuilder builder = new StringBuilder();
                        builder.append("Participants:\n");

                        int mSelectedItemsSize = mSelectedItems.size() - 1;

                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            builder.append(participantsArray.get((int) mSelectedItems.get(i)));

                            // don't put line break after the last participant
                            if (mSelectedItemsSize != i) {
                                builder.append("\n");
                            }
                        }

                        selectedParticipantsTextView.setText(builder.toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    private String[] getParticipants() {
        List<String> participants = new ArrayList<String>();

        SQLiteDatabase db = (new DatabaseOpenHelper(getContext())).getWritableDatabase();
        String query = "SELECT name FROM user;";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            participants.add(cursor.getString(0));
            while(cursor.moveToNext()) {
                participants.add(cursor.getString(0));
            }
        }

        cursor.close();
        db.close();

        String[] participantsStringArray = participants.toArray(new String[participants.size()]);
        return participantsStringArray;
    }
}
