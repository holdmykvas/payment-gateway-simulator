package pj.bankingproject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //Advising all controllers how to behave
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class) //IF illegal argument is seen -> this should be done
    public ResponseEntity<Map<String,Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String,Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Bad Request");
        errorBody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorBody,HttpStatus.BAD_REQUEST); //Returns JSON Bad_Request 400
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        Map<String, Object> errorBody = new HashMap<>();
        Map<String, String> specificError = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> specificErrors.put(error.getField(),error.getDefaultMessage()));

        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("validationErrors", specificError);

        return new ResponseEntity<>(errorBody,HttpStatus.BAD_REQUEST);
    }
}
