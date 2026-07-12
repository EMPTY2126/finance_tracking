package finance_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorBody> userNotFoundException(UserNotFoundException ex){
        HttpStatus status = ex.getStatus();
        ErrorBody error = new ErrorBody(
                status,
                "User Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBody> exceptionHandler(Exception ex){
        HttpStatus status = HttpStatus.BAD_GATEWAY;
        ErrorBody error = new ErrorBody(
                status,
                "Something Went Wrong",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, status);
    }

}
