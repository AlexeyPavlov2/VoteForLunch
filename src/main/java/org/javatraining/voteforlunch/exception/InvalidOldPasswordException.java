package org.javatraining.voteforlunch.exception;

public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException() {
        super("Invalid Old Password");
    }
}
