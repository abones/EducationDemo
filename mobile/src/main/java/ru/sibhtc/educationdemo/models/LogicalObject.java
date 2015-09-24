package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by Антон on 24.09.2015.
 **/
public class LogicalObject implements Serializable {
    public String Title;
    public String Text;
    public boolean Value;
    public String TrueText;
    public String FalseText;

    public LogicalObject(String title, String text, boolean value, String trueText, String falseText){
        Title = title;
        Text = text;
        Value = value;
        TrueText = trueText;
        FalseText = falseText;
    }

    public LogicalObject(){};
}
