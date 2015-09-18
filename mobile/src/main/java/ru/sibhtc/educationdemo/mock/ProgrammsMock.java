package ru.sibhtc.educationdemo.mock;

import java.util.ArrayList;

/**
 * Created by Антон on 17.09.2015.
 **/
public class ProgrammsMock {
    public static String[] Steps = new String[]{"Надеть каску", "Крутнуть хрень", "Крутнуть вторую хрень", "Ждать пока рванёт"};

    public static Step[] StepList = new Step[]
    {
        new Step("Надеть каску", StepResult.SUCCESS),
        new Step("Крутнуть хрень", StepResult.ERROR),
        new Step("Крутнуть вторую хрень", StepResult.SUCCESS),
        new Step("Ждать пока рванёт", StepResult.WAITING)
    };

    public static Program[] Programs = new Program[]
    {
        new Program(0, "Вывод на режим", new Step[]
        {
                new Step("Надеть каску", StepResult.SUCCESS),
                new Step("Запустить скважину", StepResult.ERROR),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Снять каску", StepResult.WAITING)
        }),
        new Program(1, "Остановка", new Step[]
        {
                new Step("Надеть каску", StepResult.SUCCESS),
                new Step("Отключить подачу э/э", StepResult.ERROR),
                new Step("Закрыть скважину", StepResult.SUCCESS),
                new Step("Снять каску", StepResult.WAITING)
        }),
        new Program(2, "Настройка", new Step[]
        {
                new Step("Надеть каску", StepResult.SUCCESS),
                new Step("Открыть вентиль", StepResult.ERROR),
                new Step("Проверить манометр", StepResult.SUCCESS),
                new Step("Снять каску", StepResult.WAITING)
        })
    };


}
