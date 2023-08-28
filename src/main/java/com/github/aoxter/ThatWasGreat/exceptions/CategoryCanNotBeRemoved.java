package com.github.aoxter.ThatWasGreat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryCanNotBeRemoved extends RuntimeException{
    public CategoryCanNotBeRemoved(String errorMessage) {
        super(errorMessage);
    }
}
