package ru.sibhtc.educationdemo.mock;

import ru.sibhtc.educationdemo.models.Label;

/**
 * Created by Антон on 23.09.2015.
 **/
public class LabelsMock {
    public static Label[] labels = new Label[]
    {
        new Label(0, "0000005E:00000026:FFFFFFDB:FFFFFFC5", "Газоанализатор", "Назначение:\nЗАМЕР ЗАГАЗОВАННОСТИ В РАБОЧЕЙ ЗОНЕ", false, "", false, null, null, null),
        new Label(1, "00000051:FFFFFFA1:00000012:FFFFFFD3", "Кран №1", "Назначение:\nПОДАЧА ГАЗА НА РЕГУЛЯТОР ДАВЛЕНИЯ (0.6 МПа).\nФактическое состояние крана: ОТКРЫТ или ЗАКРЫТ", true, "0,6", true,  null, "Открыт", "Закрыт"),
        new Label(2, "FFFFFFC1:00000037:00000014:FFFFFFD3", "Кран №2", "Назначение:\nПОДАЧА ГАЗА НА КОТЁЛ (2...3 кПа).\nФактическое состояние крана: ОТКРЫТ или ЗАКРЫТ", true, "0", true,  null, "Открыт", "Закрыт"),
        new Label(3, "FFFFFFC0:FFFFFF8A:FFFFFF91:00000004", "Пусковая пробка", "Назначение:\nПУСК РЕГУЛЯТОРА ДАВЛЕНИЯ В РАБОТУ.\nТекущее положение - ВЗВЕДЕНА или НЕ ВЗВЕДЕНА", true, "0", true, null, "Взведена", "Не взведена"),
        new Label(4, "00000061:0000003E:00000015:FFFFFFD3", "Ключ настройки", "Назначение:\nНАСТРОЙКА РЕГУЛЯТОРА ДАВЛЕНИЯ", false, "", false, null, null, null),
        new Label(5, "FFFFFFA0:0000006A:FFFFFFC3:00000004", "Регулировочная гайка", "Назначение:\nНАСТРОЙКА РЕГУЛЯТОРА ДАВЛЕНИЯ.\nКаждый оборот по часовой стрелке добавляет 0.2...0.4 кПа", true, "0.2", false, "кПа", null, null),
        new Label(6, "FFFFFF91:FFFFFFD6:00000011:FFFFFFD3", "Каска", "Назначение:\nЗАЩИТНЫЙ ГОЛОВНОЙ УБОР", false, "", false, null, null, null)
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

        for (Label label : labels) {
            if (label.labelId == id) {
                result = label.labelCode;
                isFound = true;
            }
        }

        return isFound ? result : null;
    }
}
