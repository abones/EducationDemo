package ru.sibhtc.educationdemo.helpers;

/**
 * Created by Антон on 24.09.2015.
 **/
public class MessageHelper {

    public static MessageHelper messageHelper;

    public static MessageHelper getInstance(){
        if (messageHelper == null){
            messageHelper = new MessageHelper();
        }

        return messageHelper;
    }

}
