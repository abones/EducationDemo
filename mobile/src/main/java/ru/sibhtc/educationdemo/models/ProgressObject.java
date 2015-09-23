package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by Антон on 23.09.2015.
 **/
public class ProgressObject implements Serializable {
    public String Title;
    public String Text;
    public double Value;
    public double MaxValue;
    public String Measure;

    public ProgressObject(String title, String text, double value, double maxValue, String measure){
        Title = title;
        Text = text;
        Value = value;
        MaxValue = maxValue;
        Measure = measure;
    }

    public ProgressObject(){};
}
