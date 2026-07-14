package finance_management.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidAmount extends RuntimeException {

    private HttpStatus httpStatus;
    public InvalidAmount(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
