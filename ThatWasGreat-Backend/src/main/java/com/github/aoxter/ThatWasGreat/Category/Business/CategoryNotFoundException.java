package com.github.aoxter.ThatWasGreat.Category.Business;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
