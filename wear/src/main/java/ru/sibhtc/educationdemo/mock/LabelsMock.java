package ru.sibhtc.educationdemo.mock;

import ru.sibhtc.educationdemo.models.Label;

/**
 * Created by Антон on 20.09.2015.
 **/
public class LabelsMock {
    public static Label[] labels = new Label[]
            {
                    new Label(0, "gas", "Газоанализатор", "Используется для замера загазованности в рабочей зоне ГРПШ", false),
                    new Label(1, "tap1", "Кран №1", "Открывает кран №1. Давление на датчие №1 становится 0,6 МПа", true),
                    new Label(2, "tap2", "Кран №2", "Открывает  кран №2.", true),
                    new Label(3, "tube", "Пусковая пробка", "Пусковая пробка регулятора давления", true),
                    new Label(4, "switch", "Ключ настройки", "Ключ настройки регулятора давления", false),
                    new Label(5, "nut", "Регулировочная гайка", "Закручивается по часовой стрелке. Каждый оборот 0,2...0,4 кПа", true),
                    new Label(6, "helmet", "Каска", "Одеть каску", false)
            };

    public static Label getByCode(String code){
        Label result = new Label();
        boolean isFound = false;

        for (int index = 0; index < labels.length; index++){
            if (labels[index].LabelCode.equals(code)){
                result = labels[index];
                isFound = true;
            }
        }

        return isFound ? result : null;
    }

    public static  Label getById(long id){
        Label result = null;
        boolean isFound = false;

        for (int index = 0; index < labels.length; index++){
            if (labels[index].LabelId == id){
                result = labels[index];
                isFound = true;
            }
        }

        return isFound ? result : null;
    }

    public static String getCodeById(long id){
        String result = "";
        boolean isFound = false;

        for (int index = 0; index < labels.length; index++){
            if (labels[index].LabelId == id){
                result = labels[index].LabelCode;
                isFound = true;
            }
        }

        return isFound ? result : null;
    }
}
