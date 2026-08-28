package File.Sharing.platform.File.Sharing.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(value = {userNotFound.class})
    public ResponseEntity<String> userNotFound(userNotFound userNotFound){
    return new ResponseEntity<>(userNotFound.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AlreadyExisits.class})
    public ResponseEntity<String> userAlreadyExisits(AlreadyExisits alreadyExisits){
        return  new ResponseEntity<>(alreadyExisits.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<String> StrayException(RuntimeException runtimeException){
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
