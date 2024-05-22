package com.github.aoxter.ThatWasGreat;

import com.github.aoxter.ThatWasGreat.Category.Business.CategoryCanNotBeRemovedException;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.Factor.Business.FactorAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.Factor.Business.FactorNotFoundException;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CategoryCanNotBeRemovedException.class)
    public final ResponseEntity<Object> handleCategoryCanNotBeRemovedException(CategoryCanNotBeRemovedException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public final ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = EntryAlreadyExistsException.class)
    public final ResponseEntity<Object> handleEntryAlreadyExistsException(EntryAlreadyExistsException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = FactorAlreadyExistsException.class)
    public final ResponseEntity<Object> handleFactorAlreadyExistsException(FactorAlreadyExistsException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = FactorNotFoundException.class)
    public final ResponseEntity<Object> handleFactorNotFoundException(FactorNotFoundException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    // TODO alternative 1 that skips handleExceptionInternal\

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleUnexpectedException(Exception exception) {
//        return new ResponseEntity<>(getBody(HttpStatus.INTERNAL_SERVER_ERROR, exception, "Message"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    public Map<String, Object> getBody(HttpStatus status, Exception exception, String message) {
//
//        log.error(message, exception);
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("message", message);
//        body.put("timestamp", new Date());
//        body.put("status", status.value());
//        body.put("error", status.getReasonPhrase());
//        body.put("exception", exception.toString());
//
//        Throwable cause = exception.getCause();
//        if (cause != null) {
//            body.put("exceptionCause", exception.getCause().toString());
//        }
//        return body;
//    }

// TODO alternative 2 for override handleExceptionInternal

//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, String body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return new ResponseEntity<>(body, headers, status);
//    }
}
