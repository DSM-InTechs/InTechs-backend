package InTechs.InTechs.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ExceptionResponse> basicExceptionHandler(final BaseException e){
        final ExceptionMessage message = e.getExceptionMessage();
        return new ResponseEntity<>(new ExceptionResponse(message.getStatus(), message.getMessage()), HttpStatus.valueOf(message.getStatus()));
    }
}
