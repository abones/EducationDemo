package ru.sibhtc.educationdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.models.ProgressObject;

/**
 * Created by Антон on 23.09.2015.
 **/
public class ProgressFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        Bundle bundle = getArguments();
        byte[] data = bundle.getByteArray("info");

        ProgressObject object = new ProgressObject();
        try
        {
            object = (ProgressObject) BytesHelper.toObject(data);
        }
        catch (Exception ex)
        {
            Log.d("MainActivity", "Ошибка перевода в объект");
        }




        String infoTitle = object.Title;
        String infoText = object.Text;
        double value = object.Value;
        double max = object.MaxValue;
        String measure = object.Measure;
        boolean isOver = false;
        Integer progressValue = (int)(long)Math.round((value/max)*100);
        if (progressValue > 100){
            progressValue = 100;
        }


        int decimalPlaces = 2;
        BigDecimal bd = new BigDecimal(value);

        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        value = bd.doubleValue();

        View view = inflater.inflate(R.layout.progress_fragment, container, false);
        TextView infoTtl = (TextView)view.findViewById(R.id.progressTitle);
        TextView infoTxt = (TextView)view.findViewById(R.id.progressText);
        ProgressBar progress = (ProgressBar)view.findViewById(R.id.progressBar);
        TextView measureTxt = (TextView)view.findViewById(R.id.valueText);

        infoTtl.setText(infoTitle);
        infoTxt.setText(infoText);

        progress.setMax(100);
        progress.setProgress(progressValue);

        measureTxt.setText(Double.toString(value) + " " + measure);

        return view;
    }
}
