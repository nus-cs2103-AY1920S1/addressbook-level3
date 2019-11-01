package seedu.address.model.note;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents the priority of a task represented by a Note object.
 */
public enum Priority {
    HIGH,
    MEDIUM,
    LOW,
    UNMARKED;

    public static final String INVALID_PRIORITY = "Priority must be of type high, medium, low or unmarked.";

    public static Priority getPriority(String priority) throws ParseException {
        assert priority != null;

        String upperCasePriority = priority.trim().toUpperCase();
        switch (upperCasePriority) {
        case "HIGH" :
            return HIGH;
        case "MEDIUM" :
            return MEDIUM;
        case "LOW" :
            return LOW;
        case "UNMARKED" :
            return UNMARKED;
        default :
            throw new ParseException(INVALID_PRIORITY);
        }
    }
}
