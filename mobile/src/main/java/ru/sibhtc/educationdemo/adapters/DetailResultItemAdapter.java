package ru.sibhtc.educationdemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.adapters.holders.DetailResultItemHolder;
import ru.sibhtc.educationdemo.constants.MessageStrings;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.models.EventResultModel;
import ru.sibhtc.educationdemo.models.Step;

/**
 * Created by nikpodrivnik on 28/09/15.
 */
public class DetailResultItemAdapter extends ArrayAdapter<EventResultModel> {
    private Context context;
    private int waitLayoutResourceId;
    private ArrayList<EventResultModel> data = null;

    public DetailResultItemAdapter(Context context, int waitingLayoutResource, ArrayList<EventResultModel> data) {
        super(context, waitingLayoutResource, data);
        this.waitLayoutResourceId = waitingLayoutResource;
        this.context = context;
        this.data = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DetailResultItemHolder holder = null;
        int layoutId = 0;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(waitLayoutResourceId, parent, false);

            holder = new DetailResultItemHolder();
            holder.setDateEvent((TextView) row.findViewById(R.id.date_event_value));
            holder.setTimeResult((TextView) row.findViewById(R.id.time_result_value));
            holder.setErrorCount((TextView) row.findViewById(R.id.error_count_value));
            holder.setResultRating((TextView) row.findViewById(R.id.result_rating_value));
            holder.setResultRatingImage((ImageView) row.findViewById(R.id.result_rating_image));


            row.setTag(holder);
        } else {
            holder = (DetailResultItemHolder) row.getTag();
        }

        holder.getResultRatingImage().setImageResource(R.mipmap.success);

        holder.getDateEvent().setText("11.12.2015 12:31");
        holder.getTimeResult().setText("06:08");
        holder.getErrorCount().setText("3");
        holder.getResultRating().setText("7/10");
        return row;
    }


    public synchronized void refreshAdapter(EventResultModel eventResultModel) {

        notifyDataSetChanged();
    }

}
