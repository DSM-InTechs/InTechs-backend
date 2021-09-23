package InTechs.InTechs.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    BAD_REQUEST(400, "BAD_REQUEST"),
    INVALID_TOKEN(401, "INVALID_TOKEN"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    PROJECT_NOT_FOUND(404, "PROJECT_NOT_FOUND"),
    USER_ALREADY(409, "USER_ALREADY");


    private final int status;
    private final String message;

    ExceptionMessage(int status, String message){
        this.status = status;
        this.message = message;
    }
}
