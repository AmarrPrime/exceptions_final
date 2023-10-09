package org.example;

import lombok.Getter;

@Getter
public class PersonReadingException extends RuntimeException {

    private PersonReadingError error;

    public PersonReadingException(PersonReadingError error, String message) {
        super(message);
        this.error = error;
    }

    public PersonReadingException(PersonReadingError error, String message, Exception e) {
        super(message, e);
        this.error = error;
    }
}
