package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by nikpodrivnik on 26/09/15.
 */
public class  EventModel implements Serializable {
    public String StudentName;
    public String ProgramName;

    public EventModel(String studentName, String programName){
        StudentName = studentName;
        ProgramName = programName;
    }

    public EventModel(){}
}
