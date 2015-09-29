package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by Антон on 23.09.2015.
 **/
public class LabelNFC implements Serializable{
    public long labelId;
    public String labelCode;
    public String labelName;
    public String labelDescription;
    public boolean isValued;
    public String valueMeasure;//единицы измерения
    public String   controlValue;//ожидаемое значение
    public String enteredValue; //значение снятое со стенда
    public String value; //значение снятое со стенда

    public boolean isBool;
    public String trueValue;
    public String falseValue;


    public LabelNFC(long labelId, String labelCode, String labelName, String labelDescription, boolean isValued, boolean isBool, String valueMeasure, String trueValue, String falseValue){
        this.labelId = labelId;
        this.labelCode = labelCode;
        this.labelName = labelName;
        this.labelDescription = labelDescription;
        this.isValued = isValued;
        this.isBool = isBool;
        this.valueMeasure = valueMeasure;
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    public LabelNFC(){}
}
