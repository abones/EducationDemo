package ru.sibhtc.educationdemo.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by nikpodrivnik on 27/09/15.
 */
public class MessageModel implements Serializable {
    public String labelId;// идентификатор метки
    public String labelCode; //название метки
    public String labelValue; //значение метки, если есть
    public Boolean isValued; //нужно ли проверить значение с сервера


    public MessageModel(String labelId, String labelName, String labelValue, Boolean isValued){
        this.labelId = labelId;
        this.labelCode = labelName;
        this.labelValue = labelValue;
        this.isValued = isValued;
    }

    public MessageModel() {
    }

}
