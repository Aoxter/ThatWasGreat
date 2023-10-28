package com.github.aoxter.ThatWasGreat.Category.Business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryCanNotBeRemovedException extends RuntimeException{
    public CategoryCanNotBeRemovedException(String errorMessage) {
        super(errorMessage);
    }
}
