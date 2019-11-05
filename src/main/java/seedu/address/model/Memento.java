package seedu.address.model;

//@@author weikiat97
/**
 * Represents a state of the Classroom.
 */
public class Memento {

    private ReadOnlyNotebook state;

    public Memento(ReadOnlyNotebook state) {
        this.state = state;
    }

    public ReadOnlyNotebook getState() {
        return state;
    }
}
