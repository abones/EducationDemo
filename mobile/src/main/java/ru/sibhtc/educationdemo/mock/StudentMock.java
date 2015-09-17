package ru.sibhtc.educationdemo.mock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Антон on 17.09.2015.
 **/
public class StudentMock {
    public static String[] Titles = new String[] {"Антонов А. А.", "Васильев В. В.", "Иванов И. И.", "Сергеев С. С."};

    public static ArrayList<Student> GetStudentList(){
        ArrayList<Student> StudentList = new ArrayList<Student>();
        StudentList.add(0, new Student("Антон",     "Антонов",  "Антонович",    "1", 30));
        StudentList.add(1, new Student("василий",   "Васильев", "Васильевич",   "5", 31));
        StudentList.add(2, new Student("Иван",      "Иванов",   "Иванович",     "2", 33));
        StudentList.add(3, new Student("Сергей",    "Сергеев",  "Сергеевич",    "10", 40));
        return StudentList;
    }

}
