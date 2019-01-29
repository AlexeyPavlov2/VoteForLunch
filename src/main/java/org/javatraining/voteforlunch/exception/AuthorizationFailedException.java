package org.javatraining.voteforlunch.exception;

public class AuthorizationFailedException extends RuntimeException {
    public AuthorizationFailedException() {
        super("Authorization required");
    }
}
