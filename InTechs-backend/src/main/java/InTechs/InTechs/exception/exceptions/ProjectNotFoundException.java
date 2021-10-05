package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class ProjectNotFoundException extends BaseException {

    public ProjectNotFoundException() {
        super(ExceptionMessage.PROJECT_NOT_FOUND);
    }
}
