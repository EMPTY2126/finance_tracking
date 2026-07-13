package finance_management.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTransactionAmount extends RuntimeException {

    private HttpStatus httpStatus;
    public InvalidTransactionAmount(String message, HttpStatus httpStatus) {
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
