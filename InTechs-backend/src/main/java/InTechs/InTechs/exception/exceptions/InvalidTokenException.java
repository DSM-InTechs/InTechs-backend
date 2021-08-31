package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super(ExceptionMessage.INVALID_TOKEN);
    }
}
