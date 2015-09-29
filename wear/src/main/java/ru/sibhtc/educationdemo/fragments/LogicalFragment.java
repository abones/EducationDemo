package ru.sibhtc.educationdemo.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.models.LabelNFC;


/**
 * Created by Антон on 24.09.2015.
 **/
public class LogicalFragment extends Fragment {
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

        LabelNFC object = new LabelNFC();
        try
        {
            object = (LabelNFC) BytesHelper.toObject(data);
        }
        catch (Exception ex)
        {
            Log.d("MainActivity", "Ошибка перевода в объект");
        }

        String logicalTitle = object.labelName;
        String logicalInfo = object.labelDescription;
        String logicalValue = object.value == "0" ? object.trueValue : object.falseValue;

        View view = inflater.inflate(R.layout.logical_fragment, container, false);
        TextView lgcTtl = (TextView)view.findViewById(R.id.logicalTitle);
        TextView lgcTxt = (TextView)view.findViewById(R.id.logicalText);
        TextView lgcVal = (TextView)view.findViewById(R.id.logicalValue);

        lgcTtl.setText(logicalTitle);
        lgcTxt.setText(logicalInfo);
        lgcVal.setText(logicalValue);

        return view;
    }

}
