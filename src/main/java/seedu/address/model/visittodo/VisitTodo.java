package seedu.address.model.visittodo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a VisitTodo in the application.
 * As the name suggests, Visit "Todos" are "Todos" for the community nurse when he/she goes on a visit.
 * i.e. VisitTodos are template VisitTasks.
 * This is in contrast to Visit "Tasks" which are "Tasks" that the community nurse has to do during a visit.
 * i.e. VisitTasks are tasks to be done during a visit.
 * Guarantees: immutable; description is valid as declared in {@link #isValidVisitTodoDescription(String)}
 */
public class VisitTodo {

    public static final String MESSAGE_CONSTRAINTS = "Visit Todo descriptions can "
            + "take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";


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

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if a given string is a valid visitTodo description.
     */
    public static boolean isValidVisitTodoDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameVisitTodo(VisitTodo otherVisitTodo) {
        if (otherVisitTodo == this) {
            return true;
        }

        return otherVisitTodo != null
                && otherVisitTodo.getDescription().equals(getDescription());
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
