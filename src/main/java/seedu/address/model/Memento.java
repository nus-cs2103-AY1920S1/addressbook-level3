package seedu.address.model;

/**
 * Represents a state of the Classroom.
 */
public class Memento {

    private ReadOnlyClassroom state;

    public Memento(ReadOnlyClassroom state) {
        this.state = state;
    }

    public ReadOnlyClassroom getState() {
        return state;
    }
}
