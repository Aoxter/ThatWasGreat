package com.github.aoxter.ThatWasGreat.Entry.Business;

public class EntryAlreadyExistsException extends RuntimeException {
    public EntryAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
