package org.springframework.security.authentication;

public class CredentialsExpiredException extends AccountStatusException {

    public CredentialsExpiredException(String msg) {
        super(msg);
    }

    public CredentialsExpiredException(String msg, Throwable t) {
        super(msg, t);
    }

    @Deprecated
    public CredentialsExpiredException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
