package br.com.api.studentregister.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerStudentRegister {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> exceptionHandlerEntradaInvalida(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>("Campo inválido.", HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MensagemErro {
        private String mensagemDoUsuario;
        private String mensagemDoDev;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError erre = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erre);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<StandardError> unprocessableEntityException(UnprocessableEntityException e, HttpServletRequest request) {
        StandardError erre = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erre);
    }

}