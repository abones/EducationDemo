package ru.sibhtc.educationdemo.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.sibhtc.educationdemo.models.Student;

/**
 * Created by Антон on 17.09.2015.
 **/
public class StudentMock {

    private static ArrayList<Student> studentList = new ArrayList<Student>();

    public static ArrayList<Student> getStudentList(){
        FillStudentList();
        return studentList;
    }

    public static List<String> getStudentNameList(){
        List<String> studentNameList= new ArrayList<String>();
        for (Student student: getStudentList()){
            studentNameList.add(student.GetShortName());
        }
        return  studentNameList;
    }

    public static Student getStudentById(int id){
        return getStudentList().get(id);
    }

    private static void FillStudentList(){
        if (studentList.size() == 0) {
            studentList.add(0, new Student("Антон", "Антонов", "Антонович", "1", 30));
            studentList.add(1, new Student("василий", "Васильев", "Васильевич", "5", 31));
            studentList.add(2, new Student("Иван", "Иванов", "Иванович", "2", 33));
            studentList.add(3, new Student("Сергей", "Сергеев", "Сергеевич", "10", 40));
            studentList.add(4, new Student("Антон", "Антонов", "Антонович", "1", 30));
            studentList.add(5, new Student("василий", "Васильев", "Васильевич", "5", 31));
            studentList.add(6, new Student("Иван", "Иванов", "Иванович", "2", 33));
            studentList.add(7, new Student("Сергей", "Сергеев", "Сергеевич", "10", 40));
            studentList.add(8, new Student("Антон", "Антонов", "Антонович", "1", 30));
            studentList.add(9, new Student("василий", "Васильев", "Васильевич", "5", 31));
            studentList.add(10, new Student("Иван", "Иванов", "Иванович", "2", 33));
            studentList.add(11, new Student("Сергей", "Сергеев", "Сергеевич", "10", 40));
            studentList.add(12, new Student("василий", "Васильев", "Васильевич", "5", 31));
            studentList.add(13, new Student("Иван", "Иванов", "Иванович", "2", 33));
            studentList.add(14, new Student("Сергей", "Сергеев", "Сергеевич", "10", 40));
            studentList.add(15, new Student("Антон", "Антонов", "Антонович", "1", 30));
            studentList.add(16, new Student("василий", "Васильев", "Васильевич", "5", 31));
            studentList.add(17, new Student("Иван", "Иванов", "Иванович", "2", 33));
            studentList.add(18, new Student("Сергей", "Сергеев", "Сергеевич", "10", 40));
        }
    }

}
