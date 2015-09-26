package ru.sibhtc.educationdemo.mock;

import ru.sibhtc.educationdemo.models.Program;
import ru.sibhtc.educationdemo.models.Step;

/**
 * Created by Антон on 17.09.2015.
 **/
public class ProgrammsMock {

    public static Program[] Programs = new Program[]
            {
                    new Program(0, "Оператор котельной", new Step[]
                            {
                                    new Step(0, "Надеть каску", StepResult.WAITING),
                                    new Step(1, "Проверка загазованности ", StepResult.SUCCESS),
                                    new Step(2, "Открытие крана 1", StepResult.ERROR),
                                    new Step(3, "Открытие крана 2", StepResult.SUCCESS),
                                    new Step(4, "Взвод пусковой пробки РД", StepResult.WAITING),
                                    new Step(5, "Выбор инструмента регулировки давления", StepResult.SUCCESS),
                                    new Step(6, "Настройка регулятора", StepResult.SUCCESS)

                            }),

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
            };


}
