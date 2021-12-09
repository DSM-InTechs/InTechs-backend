package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class ChatNotFoundException extends BaseException {
    public ChatNotFoundException() {
        super(ExceptionMessage.CHAT_NOT_FOUND);
    }
}
