package InTechs.InTechs.exception.exceptions;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;

public class IssueNotFoundException extends BaseException {
    public IssueNotFoundException() {
        super(ExceptionMessage.ISSUE_NOT_FOUND);
    }
}
