package ru.sibhtc.educationdemo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Антон on 18.09.2015.
 **/
public class Program {
    public Integer  programId;
    public String   programName;
    public ArrayList<Step> steps;

    public Program(Integer id, String name, ArrayList<Step> steps)
    {
            this.programId = id;
            this.programName = name;
            this.steps = steps;
    }
}
