package com.dukeacademy.commons.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    /**
     * Instantiates a new Data conversion exception.
     *
     * @param cause the cause
     */
    public DataConversionException(Exception cause) {
        super(cause);
    }

}
