package seedu.address.model.dashboard.components;

import seedu.address.model.dashboard.components.TodoName;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a to-do in Duke Cooks.
 * Guarantees: details are present and not null, field values validated and immutable.
 */
public class Todo {

    // Identity fields
    private final TodoName todoName;

    /**
     * Every field must be present and not null.
     */
    public Todo(TodoName diaryName) {
        requireAllNonNull(diaryName);
        this.todoName = diaryName;
    }


    public TodoName getTodoName() {
        return todoName;
    }

    /**
     * Returns true if both todos of the same todoName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two todos.
     */
    public boolean isSameTodo(Todo otherTodo) {
        if (otherTodo == this) {
            return true;
        }

        return otherTodo != null
                && otherTodo.getTodoName().equals(getTodoName());
    }


    /**
     * Returns true if both todos have the same identity and data fields.
     * This defines a stronger notion of equality between two todos.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Todo)) {
            return false;
        }

        Todo otherTodo = (Todo) other;
        return otherTodo.getTodoName().equals(getTodoName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(todoName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTodoName());
        return builder.toString();
    }

}
