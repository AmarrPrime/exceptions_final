package org.example;

import lombok.Data;

import java.util.Date;

@Data
public class Person {
    private String firstName;
    private String secondName;
    private String thirdName;
    private Date birthDate;
    private long phone;
    private String sex;
}
