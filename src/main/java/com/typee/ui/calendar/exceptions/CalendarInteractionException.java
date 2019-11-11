package com.typee.ui.calendar.exceptions;

/**
 * Represents an error which occurs when attempting to interact with the calendar window.
 */
public class CalendarInteractionException extends Exception {

    /**
     * Constructs a new {@code CalendarInteractionException} with the specified message.
     *
     * @param message The specified message.
     */
    public CalendarInteractionException(String message) {
        super(message);
    }

}
