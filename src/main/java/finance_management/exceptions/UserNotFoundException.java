package finance_management.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException{

    private HttpStatus status;

    public UserNotFoundException(String msg, HttpStatus status){
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
