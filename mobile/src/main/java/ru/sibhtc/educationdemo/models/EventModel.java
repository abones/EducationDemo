package ru.sibhtc.educationdemo.models;

/**
 * Created by nikpodrivnik on 26/09/15.
 */
public class EventModel {
    public String StudentName;
    public String ProgramName;

    public EventModel(String studentName, String programName){
        StudentName = studentName;
        ProgramName = programName;
    }

    public EventModel(){}
}
