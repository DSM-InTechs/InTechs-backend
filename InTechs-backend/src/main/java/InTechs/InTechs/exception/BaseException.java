package InTechs.InTechs.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private final ExceptionMessage exceptionMessage;

    public BaseException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }
}
