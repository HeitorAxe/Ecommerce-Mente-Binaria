package com.compassuol.sp.challenge.ecommerce.handler;


import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
import com.compassuol.sp.challenge.ecommerce.order.exception.PostalCodeNotFoundException;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import feign.FeignException;
import feign.RetryableException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request
            , BindingResult result){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Fields", result));
    }

    @ExceptionHandler({ProductNameUniqueViolationException.class})
    public ResponseEntity<ErrorMessage> handleUniqueViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMessage> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage()));
    }
    @ExceptionHandler({OrderStatusNotAuthorizedException.class})
    public ResponseEntity<ErrorMessage> handleNotAuthorizationStatus(OrderStatusNotAuthorizedException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorMessage> handleMethodTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ErrorMessage> handleResourceNotFound(NoResourceFoundException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, "The resource you are looking for was not found."));
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> handleMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid Data."));
    }

    @ExceptionHandler({org.hibernate.query.sqm.UnknownPathException.class})
    public ResponseEntity<ErrorMessage> handleInvalidDataAccessApiUsageException(org.hibernate.query.sqm.UnknownPathException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid query data."));
    }

    @ExceptionHandler({PostalCodeNotFoundException.class})
    public ResponseEntity<ErrorMessage> handlePostalCodeNotFoundException(PostalCodeNotFoundException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({RetryableException.class})
    public ResponseEntity<ErrorMessage> handleRetryableException(RetryableException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, "Could not connect to ViaCepApi"));
    }


    @ExceptionHandler({FeignException.BadRequest.class})
    public ResponseEntity<ErrorMessage> handle(FeignException.BadRequest ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Postal code format not supported"));
    }

}
