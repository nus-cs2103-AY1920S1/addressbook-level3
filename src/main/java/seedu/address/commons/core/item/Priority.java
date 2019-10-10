package seedu.address.commons.core.item;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Available priority levels for tasks and events.
 */
public enum Priority {
    HIGH, MEDIUM, LOW;

    /**
     * A static method to generate a priority based on the status.
     * @param status the string representation of the priority
     * @return the priority based on the string
     * @throws IllegalValueException when the string is not recognized
     */
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
