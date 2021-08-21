package InTechs.InTechs.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    BAD_REQUEST(400, "BAD_REQUEST");

    private final int status;
    private final String message;

    ExceptionMessage(int status, String message){
        this.status = status;
        this.message = message;
    }
}
