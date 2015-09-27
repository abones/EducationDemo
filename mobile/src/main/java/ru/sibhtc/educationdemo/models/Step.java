package ru.sibhtc.educationdemo.models;

import ru.sibhtc.educationdemo.mock.StepResult;

/**
 * Created by Антон on 18.09.2015.
 **/
public class Step {


    private int id;//идентификатор шага
    private Boolean needCheckValue;//нужно ли проверять параметр на контрольное значение
    private String checkValue;//контрольное значение которое должен достичь студент
    private String checkValueInterval;//если не null, то значение из интервала
    private String linkToParam;//ссылка на параметр с сервера, обычная строка с ид параметра
    private String stepTitle;
    private StepResult stepState;
    private String labelCode;//код метки

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    private Step(int id, Boolean needCheckValue, String checkValue, String checkValueInterval, String linkToParam, String stepTitle, StepResult stepState, String labelCode){
        setId(id);
        setNeedCheckValue(needCheckValue);
        setCheckValue(checkValue);
        setCheckValueInterval(checkValueInterval);
        setLinkToParam(linkToParam);
        setStepState(stepState);
        setStepTitle(stepTitle);
        setLabelCode(labelCode);

    }

    public Step(int id, String stepTitle, StepResult stepState, String labelCode){
        this(id, false, null, null , null, stepTitle, stepState, labelCode);
    }

    public Step(int id, String checkValue, String linkToParam, String stepTitle, StepResult stepState, String labelCode){
        this(id, true, checkValue, null, linkToParam, stepTitle, stepState, labelCode);
    }

    public Step(int id, String checkValue, String checkValueInterval, String linkToParam, String stepTitle, StepResult stepState, String labelCode){
        this(id, true, checkValue, checkValueInterval, linkToParam, stepTitle, stepState, labelCode);
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


    public String getCheckValueInterval() {
        return checkValueInterval;
    }

    public void setCheckValueInterval(String checkValueInterval) {
        this.checkValueInterval = checkValueInterval;
    }
}
