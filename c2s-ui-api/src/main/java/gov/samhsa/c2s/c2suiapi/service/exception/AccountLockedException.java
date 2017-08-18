package gov.samhsa.c2s.c2suiapi.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason ="Your account has been locked because of too many failed attempts to login." )
public class AccountLockedException extends RuntimeException {
    public AccountLockedException() {
    }

    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountLockedException(Throwable cause) {
        super(cause);
    }

    public AccountLockedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
