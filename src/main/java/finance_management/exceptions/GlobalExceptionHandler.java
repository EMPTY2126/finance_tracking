package finance_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(BudgetNotFoundException.class)
    public ResponseEntity<ErrorBody> userNotFoundException(BudgetNotFoundException ex){
        HttpStatus status = ex.getStatus();
        ErrorBody error = new ErrorBody(
                status,
                "Invalid Budget operation",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorBody> userNotFoundException(TransactionNotFoundException ex){
        HttpStatus status = ex.getStatus();
        ErrorBody error = new ErrorBody(
                status,
                "Invalid Transaction operation",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, status);
    }


    @ExceptionHandler(InvalidAmount.class)
    public ResponseEntity<ErrorBody> invalidTransactionAmount(InvalidAmount ex){
        HttpStatus status = ex.getHttpStatus();
        ErrorBody error = new ErrorBody(
          status,
            "Invalid Amount ",
            ex.getMessage()
        );

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorBody> enumExceptionHandelr(HttpMessageNotReadableException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = null;
        if(ex.getMessage().contains("category")){
            message = "Invalid transaction category";
        }else{
            message = "Invalid transaction type";
        }

        ErrorBody error = new ErrorBody(
                status,
                message,
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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
