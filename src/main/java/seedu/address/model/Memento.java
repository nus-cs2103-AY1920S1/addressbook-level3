package seedu.address.model;

public class Memento {

    private ReadOnlyAddressBook state;

    public Memento(ReadOnlyAddressBook state) {
        this.state = state;
    }

    public ReadOnlyAddressBook getState() {
        return state;
    }
}
