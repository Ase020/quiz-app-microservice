package com.asejnr.quiz_service.exception;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(Integer id) {
        super("Quiz with id " + id + " not found.");
    }
}
