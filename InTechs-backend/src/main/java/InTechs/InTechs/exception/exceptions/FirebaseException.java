package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class FirebaseException extends BaseException {
    public FirebaseException() {
        super(ExceptionMessage.TARGET_TOKEN_NOT_FOUND);
    }
}
