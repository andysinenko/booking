package homes.sinenko.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SettingAlreadyExistsException extends RuntimeException {

    public SettingAlreadyExistsException(String message) {
        super(message);
    }

}
