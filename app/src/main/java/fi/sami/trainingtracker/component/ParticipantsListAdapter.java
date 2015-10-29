package fi.sami.trainingtracker.component;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fi.sami.trainingtracker.R;

/**
 * Created by Sami on 29.10.2015.
 */
public class ParticipantsListAdapter extends ArrayAdapter {

    private Context context;
    private boolean useList = true;

    public ParticipantsListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView titleText;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ParticipantsListItem item = (ParticipantsListItem)getItem(position);
        View viewToUse = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            if(useList) {
                viewToUse = mInflater.inflate(R.layout.participants_list_item, null);
            } else {
                viewToUse = mInflater.inflate(R.layout.participants_grid_item, null);
            }

            holder = new ViewHolder();
            holder.titleText = (TextView)viewToUse.findViewById(R.id.titleTextView);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.titleText.setText(item.getItemTitle());
        return viewToUse;

    }
}
