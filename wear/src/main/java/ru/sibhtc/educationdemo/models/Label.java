package ru.sibhtc.educationdemo.models;

/**
 * Created by Антон on 20.09.2015.
 **/
public class Label {
    public long     LabelId;
    public String   LabelCode;
    public String   LabelName;
    public String   LabelDescription;
    public boolean  IsValued;

    public Label(long labelId, String labelCode, String labelName, String labelDescription, boolean isValued){
        LabelId = labelId;
        LabelCode = labelCode;
        LabelName = labelName;
        LabelDescription = labelDescription;
        IsValued = isValued;
    }

    public Label(){}
}
