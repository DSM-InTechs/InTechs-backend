package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class MessageNotFoundException extends BaseException {
    public MessageNotFoundException() {
        super(ExceptionMessage.MESSAGE_NOT_FOUND);
    }
}
