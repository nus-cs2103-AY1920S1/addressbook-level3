package seedu.address.commons.core.item;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Available priority levels for tasks and events.
 */
public enum Priority {
    HIGH, MEDIUM, LOW;

    public static Priority parse(String status) throws IllegalValueException {
        switch(status) {
            case "HIGH":
                return Priority.HIGH;
            case "MEDIUM":
                return Priority.MEDIUM;
            case "LOW":
                return Priority.LOW;
            default:
                throw new IllegalValueException("Priority not recognized");
        }
    }
}
