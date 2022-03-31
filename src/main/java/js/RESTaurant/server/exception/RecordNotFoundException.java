package js.RESTaurant.server.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super();
    }
    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public RecordNotFoundException(String message) {
        super(message);
    }
    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }
}
