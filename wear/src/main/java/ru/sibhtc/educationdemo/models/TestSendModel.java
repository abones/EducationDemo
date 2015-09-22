package ru.sibhtc.educationdemo.models;

import java.io.Serializable;

/**
 * Created by Антон on 22.09.2015.
 **/
public class TestSendModel implements Serializable {
    public Integer Id;
    public String Message;

    public TestSendModel(Integer id, String message){
        Id = id;
        Message = message;
    }

    public TestSendModel(){}
}
