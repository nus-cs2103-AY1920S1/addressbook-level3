package seedu.address.model.visittask;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.visittodo.VisitTodo;
/**
 * Represents a task of a Visit in the application.
 * As the name suggests, Visit "Tasks" are "Tasks" that the community nurse has to do during a visit.
 * i.e. VisitTasks are tasks to be done during a visit.
 * This is in contast to Visit "Todos" which are "Todos" for the community nurse when he/she goes on a visit.
 * i.e. VisitTodos are template VisitTasks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class VisitTask {

    // Data fields
    private final VisitTodo visitTodo;
    private final Detail detail;
    private final boolean isDone;

    /**
     * Every field must be present and not null.
     */
    public VisitTask(VisitTodo visitTodo, Detail detail, boolean isDone) {
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
    public boolean isSameVisitTask(VisitTask otherVisitTask) {
        if (otherVisitTask == this) {
            return true;
        }

        return otherVisitTask != null
                && otherVisitTask.getDetail().equals(getDetail())
                && (otherVisitTask.getVisitTodo().equals(getVisitTodo())
                || otherVisitTask.getDetail().equals(getDetail()));
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

        if (!(other instanceof VisitTask)) {
            return false;
        }

        VisitTask otherVisitTask = (VisitTask) other;
        return otherVisitTask.getDetail().equals(getDetail())
                && otherVisitTask.getVisitTodo().equals(getVisitTodo())
                && otherVisitTask.getDetail().equals(getDetail())
                && otherVisitTask.isDone() == isDone();
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
            builder.append(" (done)");
        } else {
            builder.append(" (unfinished)");
        }
        return builder.toString();
    }

}
