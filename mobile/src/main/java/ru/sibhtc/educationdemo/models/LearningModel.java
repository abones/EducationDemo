package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by nikpodrivnik on 29/09/15.
 */
public class LearningModel implements Serializable {
    public int currentStep;
    public int stepCount;
    public String stepInfo;
    public Boolean isFinish; //true значит закончен

    public LearningModel(int currentStep, int stepCount, String stepInfo){
        this.currentStep = currentStep;
        this.stepCount = stepCount;
        this.stepInfo = stepInfo;
        this.isFinish = false;
    }

    public LearningModel(){};
}
