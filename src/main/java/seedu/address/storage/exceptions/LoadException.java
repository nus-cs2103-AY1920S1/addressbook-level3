package seedu.address.storage.exceptions;

import seedu.address.commons.exceptions.AlfredException;

/**
 * Represents an error in loading from JSON file or Storage file into AddressBook when the app is reopened.
 */
public class LoadException extends AlfredException {
    public LoadException(String message, Throwable cause) {
        super(message, cause);
    }

}

