package ru.sibhtc.educationdemo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.adapters.StepAdapter;
import ru.sibhtc.educationdemo.constants.MessagePaths;
import ru.sibhtc.educationdemo.constants.MessageStrings;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.StepResult;
import ru.sibhtc.educationdemo.models.LearningModel;
import ru.sibhtc.educationdemo.models.MessageModel;
import ru.sibhtc.educationdemo.models.Program;
import ru.sibhtc.educationdemo.mock.ProgrammsMock;
import ru.sibhtc.educationdemo.mock.StudentMock;

/**
 * Created by Антон on 17.09.2015.
 **/
public class LearningFragment extends EventFragment {

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
                sendLearningMessage(completeSteps.size(), steps.size(),
                        currentStep.getStepTitle(), false);
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
        adapter = new StepAdapter(getActivity(), R.layout.step_list_item, completeSteps);
        adapter.setNotifyOnChange(true);
        listSteps.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    public void wearAnswer(MessageModel messageModel) {
        if (checkAnswer(messageModel)) {
            if (completeSteps.size() != steps.size()) {
                final Activity act = getActivity(); //если ответ верен то помечаю зеленой стрелочкой иначи жду верного ответа
                if (act != null)
                    act.runOnUiThread(new Runnable() {
                        public void run() {
                            completeSteps.get(completeSteps.size() - 1).setStepState(StepResult.SUCCESS);
                            completeSteps.get(completeSteps.size() - 1).setStepEnd(new Date());
                            currentStep = steps.get(completeSteps.size());
                            sendLearningMessage(completeSteps.size() + 1, steps.size(),
                                    currentStep.getStepTitle(), false);
                            adapter.refreshAdapter(steps.get(completeSteps.size()));
                        }
                    });
            }
            else
            {
                final Activity act = getActivity(); //проверяю последний шаг
                if (act != null)
                    act.runOnUiThread(new Runnable() {
                        public void run() {
                            completeSteps.get(completeSteps.size() - 1).setStepState(StepResult.SUCCESS);
                            sendLearningMessage(completeSteps.size(), steps.size(),
                                    currentStep.getStepTitle(), true);
                            adapter.refreshFinishedAdapter(MessageStrings.LEARNING_FINISHED);
                            String str = "";
                            GlobalHelper.sendMessage(MessagePaths.INFO_START_MESSAGE_PATH, str.getBytes());
                        }
                    });
            }
        } else {
            GlobalHelper.showToast(getContext(), MessageStrings.LEARNING_INCORRECT_ANSWER);
            GlobalHelper.sendMessage(MessagePaths.ERROR_MESSAGE_PATH, MessageStrings.LEARNING_INCORRECT_ANSWER.getBytes());
        }
    }


    //отправка сообщения с информацией о новом шаге
    private void sendLearningMessage(Integer currentStep, Integer stepCount, String stepInfo,
                                     Boolean isFinished) {
        LearningModel learningModel = new LearningModel(currentStep, stepCount, stepInfo);
        learningModel.isFinish = isFinished;
        boolean isGeneratedArray;
        String path = "";
        if (learningModel != null) {
            byte[] data;
            try {
                data = BytesHelper.toByteArray(learningModel);
                isGeneratedArray = true;
                path = MessagePaths.STUDY_MESSAGE_PATH;
            } catch (Exception ex) {
                data = new byte[]{};
                isGeneratedArray = false;
            }
            if (isGeneratedArray) {
                GlobalHelper.sendMessage(path, data);
            }
        }
    }
}
