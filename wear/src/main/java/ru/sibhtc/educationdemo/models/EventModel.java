package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by nikpodrivnik on 26/09/15.
 */
public class EventModel implements Serializable {
    public String studentName;
    public String programName;

    public EventModel(String studentName, String programName){
        this.studentName = studentName;
        this.programName = programName;
    }

    public EventModel(){}
}
