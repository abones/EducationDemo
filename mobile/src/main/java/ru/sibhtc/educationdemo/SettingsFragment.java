package ru.sibhtc.educationdemo;

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
import java.util.Iterator;
import java.util.List;

import ru.sibhtc.educationdemo.mock.LearningMock;
import ru.sibhtc.educationdemo.mock.Program;
import ru.sibhtc.educationdemo.mock.ProgrammsMock;
import ru.sibhtc.educationdemo.mock.StudentMock;

/**
 * Created by Антон on 17.09.2015.
 **/
public class SettingsFragment extends Fragment {
    private int selectedProgramm;

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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Spinner studentSpinner  = (Spinner) view.findViewById(R.id.studentSpinner);
        Spinner programSpinner  = (Spinner) view.findViewById(R.id.programSpinner);
        Button button           = (Button) view.findViewById(R.id.startLearningButton);

        List<String> programList = new ArrayList<String>();

        for(int index = 0; index < ProgrammsMock.Programs.length; index++) {

            Program program = ProgrammsMock.Programs[index];
            programList.add(program.ProgramName);
        }

        String[] programs = new String[programList.size()];
        programList.toArray(programs);

        ArrayAdapter studentsAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, StudentMock.Titles);
        ArrayAdapter programsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, programs);
// Specify the layout to use when the list of choices appears
        studentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        studentSpinner.setAdapter(studentsAdapter);
        programSpinner.setAdapter(programsAdapter);

        selectedProgramm = 0;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearningActivity activity = (LearningActivity)getActivity();
                activity.startLearning(selectedProgramm);
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

        return view;
    }
}
