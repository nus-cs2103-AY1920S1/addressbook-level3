package com.dukeacademy.logic.parser.exceptions;

import com.dukeacademy.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {

    /**
     * Instantiates a new Parse exception.
     *
     * @param message the message
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Parse exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
