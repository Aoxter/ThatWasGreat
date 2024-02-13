package com.github.aoxter.ThatWasGreat.Entry.Business;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
