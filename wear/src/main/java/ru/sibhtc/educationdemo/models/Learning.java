package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by Антон on 28.09.2015.
 **/
public class Learning implements Serializable{
    public int CurrentStep;
    public int StepCount;
    public String StepInfo;

    public Learning(int currentStep, int stepCount, String stepInfo){
        CurrentStep = currentStep;
        StepCount = stepCount;
        StepInfo = stepInfo;
    }

    public Learning(){};
}
