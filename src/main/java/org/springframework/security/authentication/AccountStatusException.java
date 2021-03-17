package org.springframework.security.authentication;

import org.springframework.security.core.AuthenticationException;

public abstract class AccountStatusException extends AuthenticationException {
    public AccountStatusException(String msg) {
        super(msg);
    }

    public AccountStatusException(String msg, Throwable t) {
        super(msg, t);
    }

    @Deprecated
    protected AccountStatusException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
