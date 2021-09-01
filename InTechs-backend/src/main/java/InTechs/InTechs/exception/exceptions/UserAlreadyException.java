package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class UserAlreadyException extends BaseException {
    public UserAlreadyException() {
        super(ExceptionMessage.USER_ALREADY);
    }
}
