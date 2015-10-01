package ru.sibhtc.educationdemo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.models.LabelNFC;
import ru.sibhtc.educationdemo.models.LearningModel;

/**
 * Created by Антон on 28.09.2015.
 **/
public class LearningWearFragment extends Fragment {
    private TextView learningStepInfo;
    private TextView learningTitle;
    private ProgressBar learningStepPosition;
    private ImageView learningStepImage;
    private TextView learningTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        Bundle bundle = getArguments();
        byte[] data = bundle.getByteArray("info");

        View view = inflater.inflate(R.layout.learning_fragment, container, false);
        learningTitle = (TextView) view.findViewById(R.id.learning_title);
        learningStepInfo = (TextView) view.findViewById(R.id.learning_step_info);
        learningStepPosition = (ProgressBar) view.findViewById(R.id.learning_step_position);
        learningStepImage = (ImageView) view.findViewById(R.id.learning_step_image);
        learningTime = (TextView) view.findViewById(R.id.learning_time);


        new CountDownTimer(3600000, 1000) {

            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

                learningTime.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                learningTime.setText("done!");
            }
        }.start();
        changeInformation(data);
        return view;
    }

    private LearningModel getObjectByByteArray(byte[] data) {
        LearningModel object = new LearningModel();
        try {
            object = (LearningModel) BytesHelper.toObject(data);
        } catch (Exception ex) {
            Log.d("MainActivity", "Ошибка перевода в объект");
        }
        return object;
    }

    public void changeInformation(final byte[] data) {

        final Activity act = getActivity(); //если ответил верно на последний вопрос
        if (act != null)
            act.runOnUiThread(new Runnable() {
                public void run() {
                    setInformation(data);
                }
            });

    }

    private void setInformation(final byte[] data) {
        LearningModel learningModel = getObjectByByteArray(data);

        learningTitle.setText(String.format("Шаг %d/%d", learningModel.currentStep, learningModel.stepCount));
        learningStepInfo.setText(learningModel.stepInfo);

        learningStepPosition.setMax(learningModel.stepCount);
        learningStepPosition.setProgress(learningModel.currentStep - 1);

    }
}
