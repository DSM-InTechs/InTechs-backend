package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class BadRequestException extends BaseException {
    public BadRequestException(){
        super(ExceptionMessage.BAD_REQUEST);
    }
}
