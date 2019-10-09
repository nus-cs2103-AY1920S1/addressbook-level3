package seedu.address.model.visittodoitem;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.visittodo.VisitTodo;

/**
 * Represents a Visit in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class VisitTodoItem {
    // Data fields
    private final VisitTodo visitTodo;
    private final Detail detail;
    private final boolean isDone;

    /**
     * Every field must be present and not null.
     */
    public VisitTodoItem(VisitTodo visitTodo, Detail detail, boolean isDone) {
        requireAllNonNull(visitTodo, detail);
        this.visitTodo = visitTodo;
        this.detail = detail;
        this.isDone = isDone;
    }

    public VisitTodo getVisitTodo() {
        return visitTodo;
    }

    public boolean isDone() {
        return isDone;
    }

    public Detail getDetail() {
        return detail;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameVisitTodoItem(VisitTodoItem otherVisitTodoItem) {
        if (otherVisitTodoItem == this) {
            return true;
        }

        return otherVisitTodoItem != null
                && otherVisitTodoItem.getDetail().equals(getDetail())
                && (otherVisitTodoItem.getVisitTodo().equals(getVisitTodo()) || otherVisitTodoItem.getDetail().equals(getDetail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VisitTodoItem)) {
            return false;
        }

        VisitTodoItem otherVisitTodoItem = (VisitTodoItem) other;
        return otherVisitTodoItem.getDetail().equals(getDetail())
                && otherVisitTodoItem.getVisitTodo().equals(getVisitTodo())
                && otherVisitTodoItem.getDetail().equals(getDetail())
                && otherVisitTodoItem.isDone() == isDone();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(visitTodo, detail, isDone);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Visit Todo: ")
                .append(getVisitTodo())
                .append(" Details: ")
                .append(getDetail());
        if (isDone()) {
            builder.append("(done)");
        } else {
            builder.append("(unfinished)");
        }
        return builder.toString();
    }

}
