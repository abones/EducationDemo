package ru.sibhtc.educationdemo.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ru.sibhtc.educationdemo.activities.ExamActivity;
import ru.sibhtc.educationdemo.activities.LearningActivity;
import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.constants.MessagePaths;
import ru.sibhtc.educationdemo.constants.MessageStrings;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.AppMode;
import ru.sibhtc.educationdemo.models.EventModel;
import ru.sibhtc.educationdemo.models.Program;
import ru.sibhtc.educationdemo.mock.ProgrammsMock;
import ru.sibhtc.educationdemo.mock.StudentMock;

/**
 * Created by Антон on 17.09.2015.
 **/
public class SettingsFragment extends Fragment {
    private int selectedProgramm;
    private int selectedStudent;

    private AppMode appMode;

    public AppMode getAppMode() {
        return appMode;
    }

    public void setAppMode(AppMode appMode) {
        this.appMode = appMode;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {

            return null;
        }
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Spinner studentSpinner = (Spinner) view.findViewById(R.id.studentSpinner);
        Spinner programSpinner = (Spinner) view.findViewById(R.id.programSpinner);
        Button startEventbutton = (Button) view.findViewById(R.id.startLearningButton);//запускает либо экзамен либо обучение

        if (appMode == AppMode.EXAMINE) {
            startEventbutton.setText(MessageStrings.START_EXAM);
        } else if (appMode == AppMode.LEARNING) {
            startEventbutton.setText(MessageStrings.START_LEARNING);
        }

        List<String> programList = new ArrayList<String>();

        for (int index = 0; index < ProgrammsMock.getPrograms().size(); index++) {

            Program program = ProgrammsMock.getPrograms().get(index);
            programList.add(program.programName);
        }

        String[] programs = new String[programList.size()];
        programList.toArray(programs);

        ArrayAdapter studentsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, StudentMock.getStudentNameList());
        ArrayAdapter programsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, programs);
// Specify the layout to use when the list of choices appears
        studentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        studentSpinner.setAdapter(studentsAdapter);
        programSpinner.setAdapter(programsAdapter);

        selectedProgramm = 0;

        startEventbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //обновляю все моки
                ProgrammsMock.resetProgramm();
                //отправляю сообщение на часы
                EventModel exam = new EventModel("Иванов", "Вывод на режим");
                byte[] data;
                String path = "";

                try {
                    data = BytesHelper.toByteArray(exam);
                    if (appMode == AppMode.LEARNING) {
                        path = MessagePaths.STUDY_MESSAGE_PATH;
                    } else if (appMode == AppMode.EXAMINE) {
                        path = MessagePaths.EXAM_MESSAGE_PATH;
                    }
                } catch (Exception ex) {
                    data = ex.getMessage().getBytes();
                    path = MessagePaths.ERROR_MESSAGE_PATH;
                }
                GlobalHelper.sendMessage(path, data);

                if (appMode == AppMode.LEARNING) {
                    LearningActivity activity = (LearningActivity) getActivity();
                    activity.startLearning(selectedProgramm, selectedStudent);
                } else if (appMode == AppMode.EXAMINE) {
                    ExamActivity activity = (ExamActivity) getActivity();
                    activity.startExam(selectedProgramm, selectedStudent);
                }
            }
        });

        programSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProgramm = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        return view;
    }
}
