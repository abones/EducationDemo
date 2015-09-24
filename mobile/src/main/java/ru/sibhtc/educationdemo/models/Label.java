package ru.sibhtc.educationdemo.models;

/**
 * Created by Антон on 23.09.2015.
 **/
public class Label {
    public long     LabelId;
    public String   LabelCode;
    public String   LabelName;
    public String   LabelDescription;
    public boolean  IsValued;
    public String   ValueMeasure;
    public String   TrueValue;
    public String   FalseValue;
    public boolean  IsBool;

    public Label(long labelId, String labelCode, String labelName, String labelDescription, boolean isValued, boolean isBool, String valueMeasure, String trueValue, String falseValue){
        LabelId = labelId;
        LabelCode = labelCode;
        LabelName = labelName;
        LabelDescription = labelDescription;
        IsValued = isValued;
        IsBool = isBool;
        ValueMeasure = valueMeasure;
        TrueValue = trueValue;
        FalseValue = falseValue;
    }

    public Label(){}
}
