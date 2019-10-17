package com.typee.model.engagement.exceptions;

/**
 * Represents an exceptional situation in which an engagement's start time occurs after or during its end time.
 */
public class InvalidTimeException extends Exception {

    /**
     * Constructs an {@code InvalidTimeException}.
     * @param message error message.
     */
    public InvalidTimeException(String message) {
        super(message);
    }

}
