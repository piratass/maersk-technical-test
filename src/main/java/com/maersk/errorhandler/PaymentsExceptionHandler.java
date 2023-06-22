package com.maersk.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.maersk.constants.PaymentsConstants;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class PaymentsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpStatusCodeException.class)
    protected ResponseEntity<Object> handleHttpRestClient(HttpStatusCodeException ex){
        PaymentsError c4f = null;
        if(ex.getStatusCode().is4xxClientError()){
            c4f = new PaymentsError(ex.getStatusCode(),
                    PaymentsConstants.PREFIX_CLIENT_ERROR);
        }else if(ex.getStatusCode().is5xxServerError()){
            c4f = new PaymentsError(ex.getStatusCode(),
                    PaymentsConstants.PREFIX_SERVER_ERROR);
        }
        c4f.setMessage(ex.getMessage());
        log.error("Error HTTP Request Client: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }


    @ExceptionHandler(PaymentsEntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(PaymentsEntityNotFoundException ex) {
        PaymentsError c4f = new PaymentsError(HttpStatus.NOT_FOUND,
                PaymentsConstants.PREFIX_CLIENT_ERROR + PaymentsConstants.NOT_FOUND);
        c4f.setMessage(ex.getMessage());
        log.error("Entity Not Found: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }


    @ExceptionHandler(PaymentsConflictException.class)
    protected ResponseEntity<Object> handleConflict(PaymentsConflictException ex) {
        PaymentsError c4f = new PaymentsError(HttpStatus.CONFLICT,
                PaymentsConstants.PREFIX_CLIENT_ERROR + PaymentsConstants.CONFLICT);
        c4f.setMessage(ex.getMessage());
        log.error("Conflict: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }

    @ExceptionHandler(PaymentsEntityUnprocessableException.class)
    protected ResponseEntity<Object> handleUnprocessableEntity(PaymentsEntityUnprocessableException ex) {
        PaymentsError c4f = new PaymentsError(HttpStatus.UNPROCESSABLE_ENTITY,
                PaymentsConstants.PREFIX_CLIENT_ERROR + PaymentsConstants.UNPROCESSABLE_ENTITY);
        c4f.setMessage(ex.getMessage());
        log.error("Unprocessable Entity: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }

    @ExceptionHandler(PaymentsUnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorized(PaymentsUnauthorizedException ex) {
        PaymentsError c4f = new PaymentsError(HttpStatus.UNAUTHORIZED,
                PaymentsConstants.PREFIX_CLIENT_ERROR + PaymentsConstants.UNAUTHORIZED);
        c4f.setMessage(ex.getMessage());
        log.error("Unauthorized: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }


    @ExceptionHandler(PaymentsGenericServerException.class)
    protected ResponseEntity<Object> handleGenericServerError(PaymentsGenericServerException ex) {
        PaymentsError c4f = null;
        if(ex.getCode()!=null){
            c4f = new PaymentsError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCode());
        }else{
            c4f = new PaymentsError(HttpStatus.INTERNAL_SERVER_ERROR,
                    PaymentsConstants.PREFIX_SERVER_ERROR + PaymentsConstants.INTERNAL_SERVER_ERROR);
        }
        c4f.setMessage(ex.getMessage());
        log.error("Internal Server Error: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        PaymentsError c4f = new PaymentsError(HttpStatus.BAD_REQUEST,
                PaymentsConstants.PREFIX_CLIENT_ERROR + PaymentsConstants.BAD_REQUEST);
        c4f.setMessage(ex.getBindingResult().getFieldError().toString());
        c4f.setSubErrors(fillValidationErrorsFrom(ex));
        log.error("Bad Request: {}", ex.getBindingResult().getFieldError().toString());
        return buildResponseEntity(c4f);
    }

    @ExceptionHandler(PaymentsGenericClientException.class)
    protected ResponseEntity<Object> handleGenericClientException(PaymentsGenericClientException ex) {
        PaymentsError c4f = new PaymentsError(ex.getHttpStatus(),
                PaymentsConstants.PREFIX_CLIENT_ERROR);
        c4f.setMessage(ex.getMessage());
        c4f.setSubErrors(ex.getSubErrors());
        log.error("Client Error: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleError(Exception ex) {
        PaymentsError civilAgreementError = new PaymentsError(HttpStatus.INTERNAL_SERVER_ERROR,
                PaymentsConstants.PREFIX_SERVER_ERROR + PaymentsConstants.INTERNAL_SERVER_ERROR);
        civilAgreementError.setMessage("Error generico de servidor " + ex.getMessage());
        log.error("Server Error: {}", ex.getMessage());
        return buildResponseEntity(civilAgreementError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new PaymentsError(HttpStatus.BAD_REQUEST,
                PaymentsConstants.PREFIX_CLIENT_ERROR + PaymentsConstants.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        log.error("Bad Request: {}", error);
        return buildResponseEntity(new PaymentsError(HttpStatus.BAD_REQUEST,
                PaymentsConstants.PREFIX_CLIENT_ERROR + PaymentsConstants.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(PaymentsError civilAgreementError) {
        return new ResponseEntity<>(civilAgreementError, civilAgreementError.getHttpStatus());
    }

    protected List<PaymentsSubError> fillValidationErrorsFrom(MethodArgumentNotValidException argumentNotValid) {
        List<PaymentsSubError> subErrorCollection = new ArrayList<>();
        argumentNotValid.getBindingResult().getFieldErrors().get(0).getRejectedValue();
        argumentNotValid.getBindingResult().getFieldErrors().stream().forEach((objError) -> {
            PaymentsSubError civilAgreementSubError = new PaymentsValidationError(objError.getObjectName(),
                    objError.getField(), objError.getRejectedValue(), objError.getDefaultMessage());
            subErrorCollection.add(civilAgreementSubError);
        });
        return subErrorCollection;
    }

}
