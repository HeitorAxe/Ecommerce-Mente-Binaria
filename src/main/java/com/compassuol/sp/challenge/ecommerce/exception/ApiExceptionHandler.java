package com.compassuol.sp.challenge.ecommerce.exception;

import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> EntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request){
        //logs original error
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> MethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request
            , BindingResult result){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "CAMPOS INVÁLIDOS", result));
    }

    @ExceptionHandler({ProductNameUniqueViolationException.class})
    public ResponseEntity<ErrorMessage> UniqueViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        //logs original error
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }
}
