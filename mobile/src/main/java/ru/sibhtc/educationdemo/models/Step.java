package ru.sibhtc.educationdemo.models;

import ru.sibhtc.educationdemo.mock.StepResult;

/**
 * Created by Антон on 18.09.2015.
 **/
public class Step {
    public String StepTitle;
    public StepResult StepState;

    public Step(String stepTitle, StepResult stepState){
        StepTitle = stepTitle;
        StepState = stepState;
    }
}
