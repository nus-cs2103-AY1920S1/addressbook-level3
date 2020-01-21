package seedu.algobase.storage.exceptions;

import java.io.IOException;

/**
 * Exception thrown when encountering Storage IO issues.
 */
public class StorageException extends IOException {

    public StorageException(String message) {
        super(message);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        return (other instanceof StorageException || other instanceof IOException)
            && this.toString().equals(other.toString());
    }
}
