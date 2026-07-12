package finance_management.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorBody{
    public HttpStatus status;
    public String msg;
    public String error;

    public ErrorBody() {

    }

    public ErrorBody(HttpStatus status, String error, String msg) {
        this.status = status;
        this.msg = msg;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
