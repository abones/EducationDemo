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

import java.util.ArrayList;
import java.util.List;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.mock.StepResult;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StepHolder holder = null;
        int layoutId = 0;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(waitLayoutResourceId, parent, false);

            holder = new StepHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.EditText01);
            holder.imageView = (ImageView)row.findViewById(R.id.imageView);
            holder.progressBar = (ProgressBar)row.findViewById(R.id.progressBar);
            row.setTag(holder);
        }
        else
        {
            holder = (StepHolder)row.getTag();
        }


        switch (data.get(position).getStepState()){
            case SUCCESS:
                holder.imageView.setImageResource(R.mipmap.success);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.INVISIBLE);
                break;
            case ERROR:
                holder.imageView.setImageResource(R.mipmap.error);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.INVISIBLE);
                break;
            case WAITING:
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.progressBar.setVisibility(View.VISIBLE);
                break;
        }

        Step step = data.get(position);
        holder.txtTitle.setText(step.getStepTitle());

        return row;
    }


    public synchronized void refreshAdapter(Step step) {
        data.add(step);
        notifyDataSetChanged();
    }

    static class StepHolder
    {
        TextView txtTitle;
        ImageView imageView;
        ProgressBar progressBar;
    }
}
