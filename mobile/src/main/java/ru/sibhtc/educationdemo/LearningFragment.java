package ru.sibhtc.educationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.sibhtc.educationdemo.adapters.StepAdapter;
import ru.sibhtc.educationdemo.constants.MessageStrings;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.StepResult;
import ru.sibhtc.educationdemo.models.MessageModel;
import ru.sibhtc.educationdemo.models.Program;
import ru.sibhtc.educationdemo.mock.ProgrammsMock;
import ru.sibhtc.educationdemo.models.Step;
import ru.sibhtc.educationdemo.mock.StudentMock;

/**
 * Created by Антон on 17.09.2015.
 **/
public class LearningFragment extends Fragment {
    private ArrayList<Step> steps = new ArrayList<Step>();
    private ArrayList<Step> completeSteps = new ArrayList<Step>();
    private ArrayList<Step> adapterSteps = new ArrayList<Step>();
    private Step currentStep;


    private View view;
    private ListView listSteps;
    private StepAdapter adapter;

    @Override
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

        Integer programId = getArguments().getInt("programId", 0);
        Integer studentID = getArguments().getInt("studentId", 0);
        String date = getArguments().getString("date");

        String studName = StudentMock.getStudentById(studentID).GetShortName();
        String programName = "";

        for (int index = 0; index < ProgrammsMock.getPrograms().size(); index++) {
            Program program = ProgrammsMock.getPrograms().get(index);
            if (program.programId == programId) {
                steps = program.steps;
                completeSteps.add(program.steps.get(0));
                currentStep = completeSteps.get(0);
                programName = program.programName;
                break;
            }
        }


        view = inflater.inflate(R.layout.fragment_learning, container, false);

        TextView prName = (TextView) view.findViewById(R.id.headerNameText);
        TextView stName = (TextView) view.findViewById(R.id.studText);
        TextView timeTxt = (TextView) view.findViewById(R.id.timeText);

        prName.setText(programName);
        stName.setText(studName);
        timeTxt.setText(date);

        listSteps = (ListView) view.findViewById(R.id.stepsList);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //adapterSteps =
        adapter = new StepAdapter(getActivity(), R.layout.step_list_item, completeSteps);
        adapter.setNotifyOnChange(true);
        //ArrayAdapter stepsAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, ProgrammsMock.Steps);
        listSteps.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private Boolean checkAnswer(MessageModel messageModel) {
        Boolean result = false;
        if (currentStep.getNeedCheckValue()) {

                String serverValue = GlobalHelper.getModelParameterByLink(currentStep.getLinkToParam());

            if (currentStep.getLabelCode().equals(messageModel.labelCode) ){
                if (currentStep.getCheckValueInterval() == null)
                {
                    if (currentStep.getCheckValue().equals(serverValue))
                        result = true;
                }else
                {
                    if (Double.parseDouble(currentStep.getCheckValue()) <= Double.parseDouble(serverValue) &&
                            Double.parseDouble(currentStep.getCheckValueInterval()) >= Double.parseDouble(serverValue) )
                    {
                        result = true;
                    }
                }
            }
        } else {
            if (currentStep.getLabelCode().equals(messageModel.labelCode)) {
                result = true;
            }
        }
        return  result;
    }

    public void wearAnswer(MessageModel messageModel) {

        if (checkAnswer(messageModel)) {

            if (completeSteps.size() != steps.size()) {
                final Activity act = getActivity(); //only neccessary if you use fragments
                if (act != null)
                    act.runOnUiThread(new Runnable() {
                        public void run() {
                            completeSteps.get(completeSteps.size() - 1).setStepState(StepResult.SUCCESS);
                            currentStep = steps.get(completeSteps.size());
                            adapter.refreshAdapter(steps.get(completeSteps.size()));
                        }
                    });
            }
            else
            {
                final Activity act = getActivity(); //only neccessary if you use fragments
                if (act != null)
                    act.runOnUiThread(new Runnable() {
                        public void run() {
                            completeSteps.get(completeSteps.size() - 1).setStepState(StepResult.SUCCESS);
                            adapter.notifyDataSetChanged();
                        }
                    });
            }
        } else {
            GlobalHelper.showToast(getContext(), MessageStrings.LEARNING_INCORRECT_ANSWER);
        }
    }
}
