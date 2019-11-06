package seedu.elisa.logic.parser.exceptions;

/**
 * Represents a parse error encountered by FastReminderDateTimeParser.
 */
public class FastReminderParseException extends ParseException {

    public FastReminderParseException(String message) {
        super(message);
    }

    public FastReminderParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
