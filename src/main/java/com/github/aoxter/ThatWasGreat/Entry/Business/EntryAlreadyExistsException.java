package com.github.aoxter.ThatWasGreat.Entry.Business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntryAlreadyExistsException extends RuntimeException {
    public EntryAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}