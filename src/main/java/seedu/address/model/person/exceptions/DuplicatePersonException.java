package seedu.address.model.person.exceptions;

import seedu.address.model.person.Name;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePersonException extends Exception {

    public DuplicatePersonException(Name name) {
        super("Duplicate Person: " + name.toString());
    }

    public DuplicatePersonException() {
    }

}
