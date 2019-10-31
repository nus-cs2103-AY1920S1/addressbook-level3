package cs.f10.t1.nursetraverse.model.visittask;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;

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
        CollectionUtil.requireAllNonNull(visitTodo, detail);
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

    /**
     * Get isDone formatted as a string.
     */
    public String getIsDoneAsString() {
        if (isDone) {
            return "✔";
        } else {
            return "✘";
        }
    }

    public Detail getDetail() {
        return detail;
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        requireNonNull(other);
        if (other == this) {
            return true;
        }

        if (!(other instanceof VisitTask)) {
            return false;
        }

        VisitTask otherVisitTask = (VisitTask) other;
        return otherVisitTask.getDetail().equals(getDetail())
                && otherVisitTask.getVisitTodo().equals(getVisitTodo())
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
            builder.append("Status: (done)");
        } else {
            builder.append("Status: (unfinished)");
        }
        return builder.toString();
    }

}
