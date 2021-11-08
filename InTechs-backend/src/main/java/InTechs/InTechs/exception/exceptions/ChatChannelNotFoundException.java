package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class ChatChannelNotFoundException extends BaseException {
    public ChatChannelNotFoundException() {
        super(ExceptionMessage.CHAT_CHANNEL_NOT_FOUND);
    }
}
