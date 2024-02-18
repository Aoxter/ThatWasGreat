package com.github.aoxter.ThatWasGreat;

import com.github.aoxter.ThatWasGreat.Category.Business.CategoryCanNotBeRemovedException;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Business.FactorAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.Category.Business.FactorNotFoundException;
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
}
