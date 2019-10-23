package seedu.address.model;

/**
 * Represents a state of the Address book.
 */
public class Memento {

    private ReadOnlyAddressBook state;

    public Memento(ReadOnlyAddressBook state) {
        this.state = state;
    }

    public ReadOnlyAddressBook getState() {
        return state;
    }
}
