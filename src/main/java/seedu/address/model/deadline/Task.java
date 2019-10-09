package seedu.address.model.deadline;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a FlashCard's Question in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuestion(String)}
 */
public class Task {

    public static final String MESSAGE_CONSTRAINTS =
            "Task can take any values, and it should not be blank";

    /*
     * The first character of the question must not be a whitespace
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String fullTask;

    /**
     * Constructs a {@code Question}.
     *
     * @param task A valid question.
     */
    public Task(String task) {
        requireNonNull(task);
        checkArgument(isValidTask(task), MESSAGE_CONSTRAINTS);
        this.fullTask = task;
    }

    /**
     * Returns true if a given string is a valid question.
     */
    public static boolean isValidTask(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullTask;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && fullTask.equals(((Task) other).fullTask)); // state check
    }

    @Override
    public int hashCode() {
        return fullTask.hashCode();
    }

}
