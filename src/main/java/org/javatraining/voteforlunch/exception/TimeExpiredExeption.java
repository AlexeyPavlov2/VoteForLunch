package org.javatraining.voteforlunch.exception;

public class TimeExpiredExeption extends RuntimeException {
    public TimeExpiredExeption (String message) {
        super(message);
    }
}
