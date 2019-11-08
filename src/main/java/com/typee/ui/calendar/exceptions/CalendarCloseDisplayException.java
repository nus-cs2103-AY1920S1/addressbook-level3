package com.typee.ui.calendar.exceptions;

/**
 * Represents an error which occurs when attempting to close a calendar display window.
 */
public class CalendarCloseDisplayException extends Exception {

    /**
     * Constructs a new {@code CalendarCloseDisplayException} with the specified message.
     * @param message The specified message.
     */
    public CalendarCloseDisplayException(String message) {
        super(message);
    }

}
