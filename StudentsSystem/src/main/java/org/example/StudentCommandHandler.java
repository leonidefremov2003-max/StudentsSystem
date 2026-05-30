package org.example;

import java.util.Map;

public class StudentCommandHandler {

    private StudentStorage studentStorage = new StudentStorage();

    public void processCommand(Command command) {
        Action action = command.getAction();
        switch (action) {
            case CREATE -> processCreateCommand(command);
            case UPDATE -> processUpdateCommand(command);
            case DELETE -> processDeleteCommand(command);
            case STATS_BY_COURSE -> processStatsByCourseCommand(command);
            case STATS_BY_CITY -> processStatsByCityCommand(command);
            case SEARCH -> processSearchCommand(command);
            default -> System.out.println("Действие не поддерживается");
        }
    }

    /**
     * Проверяет, можно ли строку интерпретировать как целое число.
     */
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Long.parseLong(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void processCreateCommand(Command command) {
        try {
            String data = command.getData();
            String[] dataArray = data.split(",");
            if (dataArray.length != 5) {
                System.out.println("Ошибка: для создания студента необходимо 5 полей: Фамилия,Имя,Курс,Город,Возраст");
                return;
            }

            String surname = dataArray[0].trim();
            String name = dataArray[1].trim();
            String course = dataArray[2].trim();
            String city = dataArray[3].trim();
            String ageStr = dataArray[4].trim();

            // Проверка, что текстовые поля не являются числами
            if (isNumeric(surname)) {
                System.out.println("Ошибка: фамилия не может быть числом.");
                return;
            }
            if (isNumeric(name)) {
                System.out.println("Ошибка: имя не может быть числом.");
                return;
            }
            if (isNumeric(course)) {
                System.out.println("Ошибка: курс не может быть числом.");
                return;
            }
            if (isNumeric(city)) {
                System.out.println("Ошибка: город не может быть числом.");
                return;
            }

            int age = Integer.parseInt(ageStr);

            Student student = new Student();
            student.setSurname(surname);
            student.setName(name);
            student.setCourse(course);
            student.setCity(city);
            student.setAge(age);

            studentStorage.createStudent(student);
            studentStorage.printALl();

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: возраст должен быть целым числом.");
        } catch (Exception e) {
            System.out.println("Ошибка при создании студента: " + e.getMessage());
        }
    }

    private void processUpdateCommand(Command command) {
        try {
            String data = command.getData();
            String[] dataArray = data.split(",");
            if (dataArray.length != 6) {
                System.out.println("Ошибка: для обновления необходимо 6 полей: ID,Фамилия,Имя,Курс,Город,Возраст");
                return;
            }

            String idStr = dataArray[0].trim();
            long id = Long.parseLong(idStr);

            String surname = dataArray[1].trim();
            String name = dataArray[2].trim();
            String course = dataArray[3].trim();
            String city = dataArray[4].trim();
            String ageStr = dataArray[5].trim();

            if (isNumeric(surname)) {
                System.out.println("Ошибка: фамилия не может быть числом.");
                return;
            }
            if (isNumeric(name)) {
                System.out.println("Ошибка: имя не может быть числом.");
                return;
            }
            if (isNumeric(course)) {
                System.out.println("Ошибка: курс не может быть числом.");
                return;
            }
            if (isNumeric(city)) {
                System.out.println("Ошибка: город не может быть числом.");
                return;
            }

            int age = Integer.parseInt(ageStr);

            Student student = new Student();
            student.setSurname(surname);
            student.setName(name);
            student.setCourse(course);
            student.setCity(city);
            student.setAge(age);

            boolean updated = studentStorage.updateStudent(id, student);
            if (updated) {
                studentStorage.printALl();
            } else {
                System.out.println("Студент с ID " + id + " не найден.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID и возраст должны быть целыми числами.");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении студента: " + e.getMessage());
        }
    }

    private void processDeleteCommand(Command command) {
        try {
            String data = command.getData();
            Long id = Long.valueOf(data.trim());
            boolean deleted = studentStorage.deleteStudent(id);
            if (deleted) {
                studentStorage.printALl();
            } else {
                System.out.println("Студент с ID " + id + " не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть целым числом.");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении студента: " + e.getMessage());
        }
    }

    private void processSearchCommand(Command command) {
        try {
            String surname = command.getData();
            studentStorage.search(surname);
        } catch (Exception e) {
            System.out.println("Ошибка при поиске: " + e.getMessage());
        }
    }

    private void processStatsByCourseCommand(Command command) {
        Map<String, Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);
    }

    private void processStatsByCityCommand(Command command) {
        Map<String, Long> data = studentStorage.getCountByCity();
        studentStorage.printMap(data);
    }
}
