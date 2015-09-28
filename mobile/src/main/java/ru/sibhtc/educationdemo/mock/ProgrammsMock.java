package ru.sibhtc.educationdemo.mock;

import java.util.ArrayList;
import java.util.List;

import ru.sibhtc.educationdemo.models.Program;
import ru.sibhtc.educationdemo.models.Step;

/**
 * Created by Антон on 17.09.2015.
 **/
public class ProgrammsMock {
    private static ArrayList<Program> programs;

    public static ArrayList<Program> getPrograms() {
        if (programs == null) {
            programs = new ArrayList<Program>();

            //программа 1 начало
            ArrayList<Step> steps = new ArrayList<Step>();
            steps.add(new Step(0, "Надеть каску", StepResult.WAITING, "helmet"));
            steps.add(new Step(1, "Проверка загазованности ", StepResult.WAITING, "gas"));
            steps.add(new Step(2, "0", "SwitchIn", "Открытие крана 1", StepResult.WAITING, "tap1"));
            steps.add(new Step(3, "0", "SwitchOut", "Открытие крана 2", StepResult.WAITING, "tap2"));
            steps.add(new Step(4, "Взвод пусковой пробки РД", StepResult.WAITING, "tube"));
            steps.add(new Step(5, "Выбор инструмента регулировки давления", StepResult.WAITING, "switch"));
            steps.add(new Step(6, "2.0", "PresureOut", "Настройка регулятора", StepResult.WAITING, "nut"));

            programs.add(new Program(0, "Оператор котельной",steps));
            //программа 1 конец
        }


        return programs;
    }



                    /*new Program(1, "Вывод на режим", new Step[]
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
                            })*/



}
