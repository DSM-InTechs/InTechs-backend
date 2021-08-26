package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(ExceptionMessage.USER_NOT_FOUND);
    }
}
