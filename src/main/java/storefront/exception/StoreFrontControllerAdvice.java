package storefront.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class StoreFrontControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        log.error("Unhandled exception for: {}", request.getDescription(false), ex);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        log.error("Throwing BadRequestException. message: {}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        log.error("Throwing MethodArgumentNotValidException. message: {}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        log.error("Throwing IllegalArgumentException. message: {}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PageNotFoundException.class)
    public ResponseEntity<ErrorMessage> handlePageNotFoundException(PageNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        log.error("Throwing PageNotFoundException. message: {}", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
