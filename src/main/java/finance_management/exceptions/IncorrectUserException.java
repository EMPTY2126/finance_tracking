package finance_management.exceptions;

import org.springframework.http.HttpStatus;

public class IncorrectUserException extends RuntimeException {

    private HttpStatus httpStatus;

    public IncorrectUserException(String message)
    {
        super(message);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
