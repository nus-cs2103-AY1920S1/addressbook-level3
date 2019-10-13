package seedu.ichifund.model.analytics.exceptions;

import seedu.ichifund.model.analytics.Report;

/**
 * Represents an error which occurs during generation of a {@link Report}.
 */
public class ReportException extends Exception {
    public ReportException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ReportException} with the specified detail {@code message} and {@code cause}.
     */
    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
