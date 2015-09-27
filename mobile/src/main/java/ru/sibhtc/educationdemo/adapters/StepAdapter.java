package ru.sibhtc.educationdemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.wallet.wobs.TimeInterval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.constants.MessageStrings;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.StepResult;
import ru.sibhtc.educationdemo.models.Step;

/**
 * Created by Антон on 18.09.2015.
 **/
public class StepAdapter extends ArrayAdapter<Step> {

    private Context context;
    private int waitLayoutResourceId;
    private ArrayList<Step> data = null;
    private Boolean lastStep;


    public StepAdapter(Context context, int waitingLayoutResource, ArrayList<Step> data) {
        super(context, waitingLayoutResource, data);
        this.waitLayoutResourceId = waitingLayoutResource;
        this.context = context;
        this.data = data;
        lastStep = false;
    }

    private long getDiffDate(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));


        return diffMinutes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StepHolder holder = null;
        int layoutId = 0;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(waitLayoutResourceId, parent, false);

            holder = new StepHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.EditText01);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);
            holder.progressBar = (ProgressBar) row.findViewById(R.id.progressBar);
            holder.stepTime = (TextView) row.findViewById(R.id.stepTime);
            row.setTag(holder);
        } else {
            holder = (StepHolder) row.getTag();
        }


        switch (data.get(position).getStepState()) {
            case SUCCESS:
                holder.imageView.setImageResource(R.mipmap.success);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.INVISIBLE);

                holder.stepTime.setText("(" +
                        getDiffDate(data.get(position).getStepStart(),
                                data.get(position).getStepEnd()) + ")");
                break;
            case ERROR:
                holder.imageView.setImageResource(R.mipmap.error);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.INVISIBLE);
                holder.stepTime.setText("(+" +
                        getDiffDate(data.get(position).getStepStart(),
                                data.get(position).getStepEnd()) + ")");
                break;
            case WAITING:
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.stepTime.setText("");
                break;
        }

        Step step = data.get(position);
        holder.txtTitle.setText(step.getStepTitle());

        return row;
    }


    public synchronized void refreshAdapter(Step step) {
        step.setStepStart(new Date());
        data.add(step);
        notifyDataSetChanged();
    }

    public synchronized void refreshFinishedAdapter(){
        data.get(data.size() - 1).setStepEnd(new Date());
        lastStep = true;
        notifyDataSetChanged();
        GlobalHelper.showToast(context, MessageStrings.LEARNING_FINISHED);
    }

    static class StepHolder {
        TextView txtTitle;
        TextView stepTime;
        ImageView imageView;
        ProgressBar progressBar;
    }
}
