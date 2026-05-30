package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentStorage {
    private Map<Long, Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();

    private Long currentId = 0L;

    /**
     * Создание данных о студенте
     * @param student  --- Данные о студенте
     * @return  --- Сгенерированный уникальный идентификатор студента
     */
    public Long createStudent(Student student) {
        Long nextId = getNextId();
        studentStorageMap.put(nextId, student);
        studentSurnameStorage.studentCreate(nextId, student.getSurname());
        return nextId;
    }

    /**
     * Обновление данных о студенте
     * @param id        --- Идентификатор студента
     * @param student   --- Данные студента
     * @return       --- true, если данные обновлены, false, если студент не был найден
     */
    public boolean updateStudent(Long id, Student student) {
        if (!studentStorageMap.containsKey(id)) {
            return false;
        } else {
            String newSurname = student.getSurname();
            String oldSurname = studentStorageMap.get(id).getSurname();
            studentSurnameStorage.studentUpdated(id, oldSurname, newSurname);
            studentStorageMap.put(id, student);
            return true;
        }
    }

    /**
     * Удаляет данные о студенте
     * @param id     --- Идентификатор студента
     * @return   --- true, если студент был удален
     * false, если студент не был найден по идентификатору
     */
    public boolean deleteStudent(Long id) {
        Student removed = studentStorageMap.remove(id);
        if (removed != null) {
          String surname = removed.getSurname();
          studentSurnameStorage.studentDeleted(id, surname);
        }
        return removed != null;

    }

    public void search(String surname) {

        if (surname == null || surname.trim().isEmpty()) {
            if (studentStorageMap.isEmpty()) {
                System.out.println("Студентов нет");
            } else {
                studentStorageMap.values().forEach(System.out::println);
            }
            return;
        }

        Set<Long> students;

        if (surname.contains(",")) {
            String[] parts = surname.split(",");
            if (parts.length != 2) {
                System.out.println("Ошибка: введите ровно две фамилии через запятую");
                return;
            }
            String first = parts[0].trim();
            String second = parts[1].trim();
            if (first.isEmpty() || second.isEmpty()) {
                System.out.println("Ошибка: фамилии не могут быть пустыми ");
                return;
            }
            students = studentSurnameStorage.getStudentsBySurnameRange(first, second);
            System.out.println("Результаты поиска в диапозоне [" + first + ", " + second + "]");
        } else {
           students = studentSurnameStorage.getStudentsBySurnameExact(surname);
            System.out.println("Результаты точного поиска по фамилии \"" + surname.trim() + "\"");
        }

        if (students.isEmpty()) {
            System.out.println("Студенты не найдены");
        } else {
            for (Long id : students) {
                System.out.println(studentStorageMap.get(id));
            }
        }
    }

    public Long getNextId() {
        currentId = currentId + 1;
        return currentId;
    }

    public void printALl() {
        System.out.println(studentStorageMap);
    }

    public void printMap(Map<String, Long> data) {
        data.entrySet().stream().forEach(e -> {
            System.out.println(e.getKey() + " - " + e.getValue());
        });
    }

    public Map<String, Long> getCountByCourse() {
       // Map<String, Long> res = new HashMap<>();
       // for (Student student : studentStorageMap.values()) {
     //       String key = student.getCourse();                              РЕАЛИЗАЦИЯ С ПОМОЩЬЮ ПРОСТЫХ ЦИКЛОВ
      //      Long count = res.getOrDefault(key, 0L);
      //      count++;
       //     res.put(key, count);
       // }
       // return res;


        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCourse(),                // РЕАЛИЗАЦИЯ С ПОМОЩЬЮ StreamApi
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
        return res;
    }

    public Map<String, Long> getCountByCity() {
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCity(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
        return res;
    }
}
