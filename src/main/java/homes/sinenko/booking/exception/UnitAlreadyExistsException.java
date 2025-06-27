package homes.sinenko.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnitAlreadyExistsException extends RuntimeException {

    public UnitAlreadyExistsException(String message) {
        super(message);
    }

}
