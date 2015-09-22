package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by Антон on 23.09.2015.
 **/
public class InfoObject implements Serializable {
    public String Title;
    public String Info;

    public InfoObject(String title, String info){
        Title = title;
        Info = info;
    }

    public InfoObject(){}
}
