package com.github.aoxter.ThatWasGreat.Category.Business;

public class CategoryCanNotBeRemovedException extends RuntimeException{
    public CategoryCanNotBeRemovedException(String errorMessage) {
        super(errorMessage);
    }
}
