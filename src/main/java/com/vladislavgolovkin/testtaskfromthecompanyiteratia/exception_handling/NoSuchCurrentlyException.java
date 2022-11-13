package com.vladislavgolovkin.testtaskfromthecompanyiteratia.exception_handling;

public class NoSuchCurrentlyException extends RuntimeException{
    public NoSuchCurrentlyException(String message) {
        super(message);
    }
}
