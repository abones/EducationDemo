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
import ru.sibhtc.educationdemo.models.InfoObject;

/**
 * Created by Антон on 23.09.2015.
 **/
public class InfoFragment extends Fragment {
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

        InfoObject object = new InfoObject();
        try
        {
            object = (InfoObject) BytesHelper.toObject(data);
        }
        catch (Exception ex)
        {
            Log.d("MainActivity", "Ошибка перевода в объект");
        }




        String infoTitle = object.Title;
        String infoText = object.Info;


        View view = inflater.inflate(R.layout.info_fragment, container, false);
        TextView infoTtl = (TextView)view.findViewById(R.id.infoTitle);
        TextView infoTxt = (TextView)view.findViewById(R.id.infoText);

        infoTtl.setText(infoTitle);
        infoTxt.setText(infoText);

        return view;
    }
}
