package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by Антон on 23.09.2015.
 **/
public class Label {
    public long labelId;
    public String labelCode;
    public String labelName;
    public String labelDescription;
    public boolean isValued;
    public String valueMeasure;//единицы измерения
    public String controlValue;//ожидаемое значение
    public boolean isBool;
    public String trueValue;
    public String falseValue;


    public Label(long labelId, String labelCode, String labelName, String labelDescription,
                 boolean isValued, String controlValue, boolean isBool, String valueMeasure,
                 String trueValue, String falseValue) {
        this.labelId = labelId;
        this.labelCode = labelCode;
        this.labelName = labelName;
        this.labelDescription = labelDescription;
        this.isValued = isValued;
        this.isBool = isBool;
        this.valueMeasure = valueMeasure;
        this.controlValue = controlValue;
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    public Label() {
    }

    public LabelNFC makeNFCCopy() {
        LabelNFC labelNFC = new LabelNFC();
        labelNFC.labelId = labelId;
        labelNFC.labelCode = labelCode;
        labelNFC.labelName = labelName;
        labelNFC.labelDescription = labelDescription;
        labelNFC.isValued = isValued;
        labelNFC.isBool = isBool;
        labelNFC.valueMeasure = valueMeasure;
        labelNFC.controlValue = controlValue;
        labelNFC.trueValue = trueValue;
        labelNFC.falseValue = falseValue;
        return labelNFC;
    }
}
