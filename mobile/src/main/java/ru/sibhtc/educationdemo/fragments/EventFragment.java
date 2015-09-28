package ru.sibhtc.educationdemo.fragments;



import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ru.sibhtc.educationdemo.adapters.StepAdapter;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.models.MessageModel;
import ru.sibhtc.educationdemo.models.Step;

/**
 * Created by nikpodrivnik on 28/09/15.
 */
public class EventFragment extends Fragment {

    protected ArrayList<Step> steps = new ArrayList<Step>();
    protected ArrayList<Step> completeSteps = new ArrayList<Step>();
    protected Step currentStep;


    protected View view;
    protected ListView listSteps;
    protected StepAdapter adapter;

    protected Boolean checkAnswer(MessageModel messageModel) {
        Boolean result = false;
        if (currentStep.getNeedCheckValue()) {

            String serverValue = GlobalHelper.getModelParameterByLink(currentStep.getLinkToParam());

            currentStep.setEnteredValue(serverValue);

            if (currentStep.getLabelCode().equals(messageModel.labelCode)) {
                if (currentStep.getCheckValueInterval() == null) {
                    if (currentStep.getCheckValue().equals(serverValue))
                        result = true;
                } else {
                    if (Double.parseDouble(currentStep.getCheckValue()) <= Double.parseDouble(serverValue) &&
                            Double.parseDouble(currentStep.getCheckValueInterval()) >= Double.parseDouble(serverValue)) {
                        result = true;
                    }
                }
            }
        } else {
            if (currentStep.getLabelCode().equals(messageModel.labelCode)) {
                result = true;
            }
        }
        return result;
    }
}
