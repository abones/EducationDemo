package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by Антон on 24.09.2015.
 **/
public class Exam implements Serializable{
    public String StudentName;
    public String ProgramName;

    public Exam(String studentName, String programName){
        StudentName = studentName;
        ProgramName = programName;
    }

    public Exam(){}
}
