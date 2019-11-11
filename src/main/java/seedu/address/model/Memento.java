package seedu.address.model;

import static java.util.Objects.requireNonNull;

//@@author weikiat97
/**
 * Represents a state of the Classroom.
 */
public class Memento {

    private ReadOnlyNotebook state;

    /** Initialises the memento and adds a ReadOnlyNotebook to it as its state. */
    public Memento(ReadOnlyNotebook state) {
        requireNonNull(state);
        this.state = state;
    }

    public ReadOnlyNotebook getState() {
        return state;
    }
}
