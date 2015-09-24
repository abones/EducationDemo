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
            new Program(0, "Оператор котельной", new Step[]
                    {
                            new Step("Проверка загазованности ", StepResult.SUCCESS),
                            new Step("Открытие крана 1", StepResult.ERROR),
                            new Step("Открытие крана 2", StepResult.SUCCESS),
                            new Step("Взвод пусковой пробки РД", StepResult.WAITING),
                            new Step("Выбор инструмента регулировки давления", StepResult.SUCCESS),
                            new Step("Настройка регулятора", StepResult.SUCCESS)

                    }),

        new Program(1, "Вывод на режим", new Step[]
        {
                new Step("Надеть каску", StepResult.SUCCESS),
                new Step("Запустить скважину", StepResult.ERROR),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Снять каску", StepResult.WAITING),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Установить дебит", StepResult.SUCCESS),
                new Step("Установить дебит", StepResult.SUCCESS)

        }),
        new Program(2, "Остановка", new Step[]
        {
                new Step("Надеть каску", StepResult.SUCCESS),
                new Step("Отключить подачу э/э", StepResult.ERROR),
                new Step("Закрыть скважину", StepResult.SUCCESS),
                new Step("Снять каску", StepResult.WAITING)
        }),
        new Program(3, "Настройка", new Step[]
        {
                new Step("Надеть каску", StepResult.SUCCESS),
                new Step("Открыть вентиль", StepResult.ERROR),
                new Step("Проверить манометр", StepResult.SUCCESS),
                new Step("Снять каску", StepResult.WAITING)
        })
    };


}
