package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Person person = null;
        while (person == null) {
            try {
                person = readPerson();
            } catch (PersonReadingException e) {
                person = null;
                System.out.println(e.getError());
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        writePerson(person);
    }

    private static Person readPerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите информацию о человеке: фамилия имя отчество датарождения номертелефона пол");
        String personInfo = scanner.nextLine();
        String[] fields = personInfo.split(" ");
        if (fields.length < 6) {
            throw new PersonReadingException(PersonReadingError.NOT_ENOUGH_DATA, "Данных недостаточно");
        } else if (fields.length > 6) {
            throw new PersonReadingException(PersonReadingError.TOO_MUCH_DATA, "Данные избыточны");
        }

        Person person = new Person();
        person.setFirstName(fields[0]);
        person.setSecondName(fields[1]);
        person.setThirdName(fields[2]);
        Date date = parseDate(fields[3]);
        person.setBirthDate(date);
        try {
            long phone = Integer.parseInt(fields[4]);
            person.setPhone(phone);
        } catch (NumberFormatException e) {
            throw new PersonReadingException(PersonReadingError.WRONG_PHONE_FORMAT, "Телефон введен неправильно", e);
        }

        if (!"f".equals(fields[5]) && !"m".equals(fields[5])) {
            throw new PersonReadingException(PersonReadingError.WRONG_SEX, "Неправильно введен пол");
        }
        person.setSex(fields[5]);

        return person;
    }

    private static Date parseDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        try {
            Date date = format.parse(string);

            return date;
        } catch (ParseException e) {
            throw new PersonReadingException(PersonReadingError.WRONG_DATE_FORMAT, "Дата имеет неправильный формат", e);
        }
    }

    private static void writePerson(Person person)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        String date = dateFormat.format(person.getBirthDate());
        Writer writer = null;
        try {
            writer = new FileWriter(person.getFirstName() + ".txt", true);
            writer.append(person.getFirstName() + " " + person.getSecondName() + " " + person.getThirdName() + " " +
                    date + " " + person.getPhone() + " " + person.getSex() + "\n");
        } catch (IOException e) {
            System.out.println("Произошла ошибка при открытии файла");
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Произошла ошибка при закрытии файла");
                    e.printStackTrace();
                }
            }
        }
    }
}