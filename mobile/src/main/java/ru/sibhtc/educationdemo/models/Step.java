package ru.sibhtc.educationdemo.models;

import ru.sibhtc.educationdemo.mock.StepResult;

/**
 * Created by Антон on 18.09.2015.
 **/
public class Step {

    private String stepTitle;
    private StepResult stepState;
    private int id;
    private Boolean needCheckValue;
    private String checkValue;
    private String linkToParam;

    private Step(int id, Boolean needCheckValue, String checkValue, String linkToParam, String stepTitle, StepResult stepState){
        setId(id);
        setNeedCheckValue(needCheckValue);
        setCheckValue(checkValue);
        setLinkToParam(linkToParam);
        setStepState(stepState);
        setStepTitle(stepTitle);
    }

    public Step(int id, String stepTitle, StepResult stepState){
        this(id, false, null, null, stepTitle, stepState);
    }

    public Step(int id, String checkValue, String linkToParam, String stepTitle, StepResult stepState){
        this(id, true, checkValue, linkToParam, stepTitle, stepState);
    }

    public String getLinkToParam() {
        return linkToParam;
    }

    public void setLinkToParam(String linkToParam) {
        this.linkToParam = linkToParam;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public Boolean getNeedCheckValue() {
        return needCheckValue;
    }

    public void setNeedCheckValue(Boolean needCheckValue) {
        this.needCheckValue = needCheckValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StepResult getStepState() {
        return stepState;
    }

    public void setStepState(StepResult stepState) {
        this.stepState = stepState;
    }

    public String getStepTitle() {
        return stepTitle;
    }

    public void setStepTitle(String stepTitle) {
        this.stepTitle = stepTitle;
    }




}
