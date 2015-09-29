package ru.sibhtc.educationdemo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.models.EventModel;
import ru.sibhtc.educationdemo.models.EventResultModel;
import ru.sibhtc.educationdemo.models.Exam;
import ru.sibhtc.educationdemo.models.LabelNFC;

/**
 * Created by Антон on 24.09.2015.
 **/
public class ExamWearFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        Bundle bundle = getArguments();
        byte[] data = bundle.getByteArray("info");

        EventModel object = new EventModel();
        try
        {
            object = (EventModel) BytesHelper.toObject(data);
        }
        catch (Exception ex)
        {
            Log.d("MainActivity", "Ошибка перевода в объект");
        }


        String student = object.studentName;
        String program = object.programName;

        View view = inflater.inflate(R.layout.exam_fragment, container, false);
        final TextView timerText = (TextView)view.findViewById(R.id.timerText);
        TextView studentText = (TextView)view.findViewById(R.id.studNameText);
        TextView programText = (TextView)view.findViewById(R.id.programNameText);

        timerText.setText("60:00");
        studentText.setText(student);
        programText.setText(program);

        new CountDownTimer(3600000, 1000) {

            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

                timerText.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                timerText.setText("done!");
            }
        }.start();

        return view;
    }


}
