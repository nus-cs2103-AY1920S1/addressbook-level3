package seedu.address.model.commanditem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the functionality of a Command.
 * Guarantees: immutable; is valid as declared in {@link #isValidTask(String)}
 */
public class CommandTask {

    public static final String MESSAGE_CONSTRAINTS =
            "Not a valid execution!";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String task;

    /**
     * Constructs a {@code CommandTask}.
     *
     * @param task A valid task.
     */
    public CommandTask(String task) {
        requireNonNull(task);
        checkArgument(isValidTask(task), MESSAGE_CONSTRAINTS);
        this.task = task;
    }

    /**
     * Returns true if a given string is a valid executable.
     */
    public static boolean isValidTask(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return task;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandTask // instanceof handles nulls
                && task.equals(((CommandTask) other).task)); // state check
    }

    @Override
    public int hashCode() {
        return task.hashCode();
    }
}
