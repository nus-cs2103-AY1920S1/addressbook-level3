package seedu.address.model;

import static java.util.Objects.requireNonNull;

//@@author weikiat97
/**
 * Represents a state of the Classroom.
 */
public class Memento {

    private ReadOnlyNotebook state;

    public Memento(ReadOnlyNotebook state) {
        requireNonNull(state);
        this.state = state;
    }

    public ReadOnlyNotebook getState() {
        return state;
    }
}
