package ru.sibhtc.educationdemo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.models.EventModel;
import ru.sibhtc.educationdemo.models.EventResultModel;
import ru.sibhtc.educationdemo.models.LabelNFC;

/**
 * Created by nikpodrivnik on 30/09/15.
 */
public class ExamWearResultFragment extends Fragment {
    private TextView examTotalTime;
    private TextView examErrorCount;
    private TextView examRatingValue;
    private ImageView examResultImage;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        Bundle bundle = getArguments();

        view = inflater.inflate(R.layout.exam_result_fragment, container, false);
        examTotalTime = (TextView) view.findViewById(R.id.examTotalTime);
        examErrorCount = (TextView) view.findViewById(R.id.examErrorCount);
        examRatingValue = (TextView) view.findViewById(R.id.examRatingValue);
        examResultImage = (ImageView) view.findViewById(R.id.examResultImage);

        setInformation(bundle.getByteArray("info"));

        return view;
    }

    private EventResultModel getObjectByByteArray(byte[] data) {
        EventResultModel object = new EventResultModel();
        try {
            object = (EventResultModel) BytesHelper.toObject(data);
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
        EventResultModel eventModel = getObjectByByteArray(data);
        examTotalTime.setText("20:15");
        examErrorCount.setText("2");
        examRatingValue.setText("1/10");
        examResultImage.setImageResource(R.mipmap.success);
        /*examTotalTime.setText(String.valueOf(eventModel.getTimeResult() % 60) + ":" +
                String.valueOf(eventModel.getTimeResult() - (eventModel.getTimeResult() % 60)));
        examErrorCount.setText(String.valueOf(eventModel.getErrorCount()));
        examRatingValue.setText(String.valueOf(eventModel.getAnswerCount() - eventModel.getErrorCount()) + "/" +
                String.valueOf(eventModel.getAnswerCount()));

        if (eventModel.getErrorCount() == 0) {
            examResultImage.setImageResource(R.mipmap.success);
        } else {
            examResultImage.setImageResource(R.mipmap.error);
        }*/
    }
}
