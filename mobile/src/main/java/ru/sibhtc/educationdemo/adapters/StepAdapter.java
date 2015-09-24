package ru.sibhtc.educationdemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.models.Step;

/**
 * Created by Антон on 18.09.2015.
 **/
public class StepAdapter extends ArrayAdapter<Step> {

    Context context;
    int waitLayoutResourceId;
    int successLayoutResourceId;
    int errorLayoutResourceId;
    Step data[] = null;

    public StepAdapter(Context context, int waitingLayoutResource, int successLayoutResource, int errorLayoutResource, Step[] data) {
        super(context, successLayoutResource, data);
        this.waitLayoutResourceId = waitingLayoutResource;
        this.successLayoutResourceId = successLayoutResource;
        this.errorLayoutResourceId = errorLayoutResource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StepHolder holder = null;
        int layoutId = 0;

        switch (data[position].StepState){
            case SUCCESS:
                layoutId = successLayoutResourceId;
                break;
            case ERROR:
                layoutId = errorLayoutResourceId;
                break;
            case WAITING:
                layoutId = waitLayoutResourceId;
                break;
        }


        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            holder = new StepHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.EditText01);

            row.setTag(holder);
        }
        else
        {
            holder = (StepHolder)row.getTag();
        }

        Step step = data[position];
        holder.txtTitle.setText(step.StepTitle);

        return row;
    }

    static class StepHolder
    {
        TextView txtTitle;
    }
}
