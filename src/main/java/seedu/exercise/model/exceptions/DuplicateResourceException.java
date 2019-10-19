package seedu.exercise.model.exceptions;

/**
 * Represents an exception which occurs if 2 identical {@code Resource} objects are
 * going to be added into the same {@code UniqueResourceList}
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException() {
        super("Operation would result in duplicate exercises/regimes/schedules.");
    }
}
