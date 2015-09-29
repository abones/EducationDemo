package ru.sibhtc.educationdemo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.adapters.StepAdapter;
import ru.sibhtc.educationdemo.constants.MessagePaths;
import ru.sibhtc.educationdemo.constants.MessageStrings;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.AppMode;
import ru.sibhtc.educationdemo.mock.ProgrammsMock;
import ru.sibhtc.educationdemo.mock.StepResult;
import ru.sibhtc.educationdemo.mock.StudentMock;
import ru.sibhtc.educationdemo.models.MessageModel;
import ru.sibhtc.educationdemo.models.Program;
import ru.sibhtc.educationdemo.models.Step;

/**
 * Created by Антон on 17.09.2015.
 **/
public class ExamFragment extends EventFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
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
                completeSteps.get(0).setStepStart(new Date());
                programName = program.programName;
                break;
            }
        }


        view = inflater.inflate(R.layout.fragment_exam, container, false);

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
        adapter = new StepAdapter(getActivity(), R.layout.step_list_item, completeSteps);
        adapter.setNotifyOnChange(true);
        listSteps.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }



    public void wearAnswer(MessageModel messageModel) {

        if (checkAnswer(messageModel)) {
            GlobalHelper.sendMessage(MessagePaths.ERROR_MESSAGE_PATH, MessageStrings.EXAM_ANSWER_APPLIED.getBytes());
            if (completeSteps.size() != steps.size()) {
                final Activity act = getActivity(); //only neccessary if you use fragments
                if (act != null)
                    act.runOnUiThread(new Runnable() {
                        public void run() {
                            completeSteps.get(completeSteps.size() - 1).setStepState(StepResult.SUCCESS);
                            completeSteps.get(completeSteps.size() - 1).setStepEnd(new Date());
                            currentStep = steps.get(completeSteps.size());
                            adapter.refreshAdapter(steps.get(completeSteps.size()));
                        }
                    });
            } else {
                final Activity act = getActivity(); //если ответил верно на последний вопрос
                if (act != null)
                    act.runOnUiThread(new Runnable() {
                        public void run() {
                            completeSteps.get(completeSteps.size() - 1).setStepState(StepResult.SUCCESS);
                            adapter.refreshFinishedAdapter();

                            //показываем результаты экзамена
                            LearningResultFragment learningResultFragment = new LearningResultFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fTrans = fragmentManager.beginTransaction();

                            fTrans.replace(R.id.examTabFrame, learningResultFragment, "LEARNING");
                            fTrans.commit();
                        }
                    });
            }
        } else {
            if (completeSteps.size() != steps.size()) {
            final Activity act = getActivity(); //если ответил с ошибкой
            if (act != null)
                act.runOnUiThread(new Runnable() {
                    public void run() {
                        completeSteps.get(completeSteps.size() - 1).setStepState(StepResult.ERROR);
                        completeSteps.get(completeSteps.size() - 1).setStepEnd(new Date());
                        currentStep = steps.get(completeSteps.size() - 1);
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
                            completeSteps.get(completeSteps.size() - 1).setStepState(StepResult.ERROR);
                            adapter.refreshFinishedAdapter();

                            //показываем результаты экзамена
                            LearningResultFragment learningResultFragment = new LearningResultFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fTrans = fragmentManager.beginTransaction();

                            fTrans.replace(R.id.examTabFrame, learningResultFragment, "LEARNING");
                            fTrans.commit();
                        }
                    });
            }
        }
    }
}
