package ru.sibhtc.educationdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import ru.sibhtc.educationdemo.adapters.StepAdapter;
import ru.sibhtc.educationdemo.models.Program;
import ru.sibhtc.educationdemo.mock.ProgrammsMock;
import ru.sibhtc.educationdemo.models.Step;
import ru.sibhtc.educationdemo.models.Student;
import ru.sibhtc.educationdemo.mock.StudentMock;

/**
 * Created by Антон on 17.09.2015.
 **/
public class LearningFragment extends Fragment {
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
        Step[] steps = new Step[]{};
        Student student = new Student();
        String studName = StudentMock.getStudentById(studentID).GetShortName();
        String programName = "";

        for (int index = 0; index < ProgrammsMock.Programs.length; index++){
            Program program = ProgrammsMock.Programs[index];
            if (program.ProgramId == programId){
                steps = program.Steps;
                programName = program.ProgramName;
            }
        }



        View view = inflater.inflate(R.layout.fragment_learning, container, false);

        TextView prName = (TextView) view.findViewById(R.id.headerNameText);
        TextView stName = (TextView) view.findViewById(R.id.studText);
        TextView timeTxt = (TextView) view.findViewById(R.id.timeText);

        prName.setText(programName);
        stName.setText(studName);
        timeTxt.setText(date);

        ListView list = (ListView)view.findViewById(R.id.stepsList);
        StepAdapter adapter = new StepAdapter(getActivity(), R.layout.step_list_item_waiting, R.layout.step_list_item_success, R.layout.step_list_item_error, steps);

        //ArrayAdapter stepsAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, ProgrammsMock.Steps);
        list.setAdapter(adapter);


        return view;
    }
}
