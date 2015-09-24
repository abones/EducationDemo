package ru.sibhtc.educationdemo.models;

/**
 * Created by Антон on 18.09.2015.
 **/
public class Program {
    public Integer  ProgramId;
    public String   ProgramName;
    public Step[]   Steps;

    public Program(Integer id, String name, Step[] steps)
    {
            ProgramId = id;
            ProgramName = name;
            Steps = steps;
    }
}
