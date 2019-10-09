package seedu.address.model.visittodo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a VisitTodo in the application.
 * Guarantees: immutable; description is valid as declared in {@link #isValidVisitTodoDescription(String)}
 */
public class VisitTodo {

    public static final String MESSAGE_CONSTRAINTS = "VisitTodos descriptions should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String description;

    /**
     * Constructs a {@code VisitTodo}.
     *
     * @param description A valid visitTodo description.
     */
    public VisitTodo(String description) {
        requireNonNull(description);
        checkArgument(isValidVisitTodoDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid visitTodo description.
     */
    public static boolean isValidVisitTodoDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitTodo // instanceof handles nulls
                && description.equals(((VisitTodo) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + description + ']';
    }

}
