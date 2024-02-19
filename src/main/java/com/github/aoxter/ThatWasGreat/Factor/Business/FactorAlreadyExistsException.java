package com.github.aoxter.ThatWasGreat.Factor.Business;

public class FactorAlreadyExistsException extends RuntimeException {
    public FactorAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

}
