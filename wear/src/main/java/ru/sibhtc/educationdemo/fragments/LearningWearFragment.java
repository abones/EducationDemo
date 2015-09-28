package ru.sibhtc.educationdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.models.Exam;
import ru.sibhtc.educationdemo.models.Learning;

/**
 * Created by Антон on 28.09.2015.
 **/
public class LearningWearFragment extends Fragment {
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

        Learning object = new Learning();
        try
        {
            object = (Learning) BytesHelper.toObject(data);
        }
        catch (Exception ex)
        {
            Log.d("MainActivity", "Ошибка перевода в объект");
        }

        View view = inflater.inflate(R.layout.learning_fragment, container, false);
        TextView learningTitle  = (TextView)view.findViewById(R.id.learning_title);
        TextView learningStepInfo = (TextView)view.findViewById(R.id.learning_step_info);

        learningTitle.setText(String.format("Шаг %d/%d", object.CurrentStep, object.StepCount));
        learningStepInfo.setText(object.StepInfo);

        return view;
    }
}
