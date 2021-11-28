package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class ChannelNotFoundException extends BaseException {
    public ChannelNotFoundException() {
        super(ExceptionMessage.CHANNEL_NOT_FOUND);
    }
}
