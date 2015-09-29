package ru.sibhtc.educationdemo.fragments;

import android.app.Activity;
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
 * Created by Антон on 23.09.2015.
 **/
public class InfoFragment extends Fragment {
    private View view;
    private TextView infoTitle;
    private TextView infoControlValue;
    private TextView infoText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        Bundle bundle = getArguments();

        LabelNFC infoObject = getObjectByByteArray(bundle.getByteArray("info"));

        view = inflater.inflate(R.layout.info_fragment, container, false);
        infoTitle = (TextView)view.findViewById(R.id.infoTitle);
        infoText = (TextView)view.findViewById(R.id.infoText);
        infoControlValue = (TextView)view.findViewById(R.id.infoControlValue);

        infoTitle.setText(infoObject.labelName);
        infoControlValue.setText(infoObject.valueMeasure);
        infoText.setText(infoObject.labelDescription);

        return view;
    }

    private LabelNFC getObjectByByteArray(byte[] data){
        LabelNFC object = new LabelNFC();
        try
        {
            object = (LabelNFC) BytesHelper.toObject(data);
        }
        catch (Exception ex)
        {
            Log.d("MainActivity", "Ошибка перевода в объект");
        }
        return object;
    }

    public void changeInformation(final byte[] data){

        final Activity act = getActivity(); //если ответил верно на последний вопрос
        if (act != null)
            act.runOnUiThread(new Runnable() {
                public void run() {
                    LabelNFC infoObject = getObjectByByteArray(data);
                    infoTitle.setText(infoObject.labelName);
                    infoControlValue.setText(infoObject.valueMeasure);
                    infoText.setText(infoObject.labelDescription);
                }
        });

    }
}
