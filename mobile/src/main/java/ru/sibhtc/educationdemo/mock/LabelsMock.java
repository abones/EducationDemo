package ru.sibhtc.educationdemo.mock;

import ru.sibhtc.educationdemo.models.Label;

/**
 * Created by Антон on 23.09.2015.
 **/
public class LabelsMock {
    public static Label[] labels = new Label[]
    {
        new Label(0, "gas", "Газоанализатор", "Используется для замера загазованности в рабочей зоне ГРПШ", false, false, null, null, null),
        new Label(1, "tap1", "Кран №1", "Открывает кран №1. Давление на датчие №1 становится 0,6 МПа", true, true,  null, "Открыт", "Закрыт"),
        new Label(2, "tap2", "Кран №2", "Открывает  кран №2.", true, true,  null, "Открыт", "Закрыт"),
        new Label(3, "tube", "Пусковая пробка", "Пусковая пробка регулятора давления", true, true, null, "Взведена", "Не взведена"),
        new Label(4, "switch", "Ключ настройки", "Ключ настройки регулятора давления", false, false, null, null, null),
        new Label(5, "nut", "Регулировочная гайка", "Закручивается по часовой стрелке. Каждый оборот 0,2...0,4 кПа", true, false, "кПа", null, null),
        new Label(6, "helmet", "Каска", "Надевается на голову", false, false, null, null, null)
    };

    public static Label getByCode(String code){
        Label result = new Label();
        boolean isFound = false;

        for (int index = 0; index < labels.length; index++){
            if (labels[index].labelCode.equals(code)){
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
            if (labels[index].labelId == id){
                result = labels[index].labelCode;
                isFound = true;
            }
        }

        return isFound ? result : null;
    }
}
