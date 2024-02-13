package com.github.aoxter.ThatWasGreat.Category.Business;

public class FactorAlreadyExistsException extends RuntimeException {
    public FactorAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

}
