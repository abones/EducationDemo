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
import ru.sibhtc.educationdemo.adapters.holders.StepHolder;
import ru.sibhtc.educationdemo.constants.MessageStrings;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.models.Step;

/**
 * Created by Антон on 18.09.2015.
 **/
public class StepAdapter extends ArrayAdapter<Step> {

    private Context context;
    private int waitLayoutResourceId;
    private ArrayList<Step> data = null;


    public StepAdapter(Context context, int waitingLayoutResource, ArrayList<Step> data) {
        super(context, waitingLayoutResource, data);
        this.waitLayoutResourceId = waitingLayoutResource;
        this.context = context;
        this.data = data;
    }

    private String getDiffDate(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long diffSeconds = diff / 1000;

        return String.valueOf(diffSeconds / 60) + ":" + String.valueOf(diffSeconds - (diffSeconds / 60) * 60);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StepHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(waitLayoutResourceId, parent, false);

            holder = new StepHolder();
            holder.setTxtTitle((TextView) row.findViewById(R.id.EditText01));
            holder.setImageView((ImageView) row.findViewById(R.id.imageView));
            holder.setProgressBar((ProgressBar) row.findViewById(R.id.progressBar));
            holder.setStepTime((TextView) row.findViewById(R.id.stepTime));
            holder.setStepValue((TextView) row.findViewById(R.id.stepValue));
            row.setTag(holder);
        } else {
            holder = (StepHolder) row.getTag();
        }

        switch (data.get(position).getStepState()) {
            case SUCCESS:
                holder.getImageView().setImageResource(R.mipmap.success);
                holder.getImageView().setVisibility(View.VISIBLE);
                holder.getProgressBar().setVisibility(View.INVISIBLE);

                holder.getStepTime().setText("(" +
                        getDiffDate(data.get(position).getStepStart(),
                                data.get(position).getStepEnd()) + ")");
                if (data.get(position).getEnteredValue() != null) {
                    if (data.get(position).getEnteredValue().equals("0")) {
                        holder.getStepValue().setText("Открыт");
                    } else {
                        if (data.get(position).getEnteredValue().equals("1")) {
                            holder.getStepValue().setText("Закрыт");
                        } else {
                            holder.getStepValue().setText(data.get(position).getEnteredValue());
                        }
                    }
                }
                holder.getStepValue().setTextColor(Color.BLACK);
                break;
            case ERROR:
                holder.getImageView().setImageResource(R.mipmap.error);
                holder.getImageView().setVisibility(View.VISIBLE);
                holder.getProgressBar().setVisibility(View.INVISIBLE);
                holder.getStepTime().setText("(" +
                        getDiffDate(data.get(position).getStepStart(),
                                data.get(position).getStepEnd()) + ")");
                if (data.get(position).getEnteredValue() != null) {
                    if (data.get(position).getEnteredValue().equals("0")) {
                        holder.getStepValue().setText("Открыт");
                    } else {
                        if (data.get(position).getEnteredValue().equals("1")) {
                            holder.getStepValue().setText("Закрыт");
                        } else {
                            holder.getStepValue().setText(data.get(position).getEnteredValue());
                        }
                    }
                }
                holder.getStepValue().setTextColor(Color.RED);

                break;
            case WAITING:
                holder.getImageView().setVisibility(View.INVISIBLE);
                holder.getProgressBar().setVisibility(View.VISIBLE);
                holder.getStepTime().setText("");
                holder.getStepValue().setText("");
                break;
        }

        Step step = data.get(position);
        holder.getTxtTitle().setText(String.valueOf(position + 1) + ". " + step.getStepTitle());

        return row;
    }


    public synchronized void refreshAdapter(Step step) {
        step.setStepStart(new Date());
        data.add(step);
        notifyDataSetChanged();
    }

    public synchronized void refreshFinishedAdapter() {
        data.get(data.size() - 1).setStepEnd(new Date());
        notifyDataSetChanged();
        GlobalHelper.showToast(context, MessageStrings.LEARNING_FINISHED);
    }


}
