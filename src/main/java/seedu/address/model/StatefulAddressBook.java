package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@code AddressBook} which maintains a stateful list of address book.
 * Useful for undo/redo functions.
 *
 * Code for StatefulAddressBook is adapted from addressbook-level4, which can be found at
 * {@link https://github.com/se-edu/addressbook-level4}.
 */
public class StatefulAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> statefulAddressBookList;
    private int currentStatePointer;

    /**
     * @param addressBook Address book to initialise the stateful address book with
     */
    public StatefulAddressBook(ReadOnlyAddressBook addressBook) {
        super(addressBook);

        this.currentStatePointer = 0;
        this.statefulAddressBookList = new ArrayList<>();
        this.statefulAddressBookList.add(new AddressBook(addressBook));
    }

    /**
     * Saves a copy of the current person list at the end of {@code statefulPersonList}.
     */
    public void saveAddressBookState() {
        clearStatesAfterCurrentPointer();

        statefulAddressBookList.add(new AddressBook(this));
        currentStatePointer++;
    }

    /**
     * All the states after the current state pointer are cleared, because performing any data change
     * while on a previous state essentially means all undo and redos should relate to the state changes from the
     * undone point.
     */
    private void clearStatesAfterCurrentPointer() {
        statefulAddressBookList.subList(currentStatePointer + 1, statefulAddressBookList.size()).clear();
    }

    // TODO: Add undo exception.
    /**
     * Restores our address book to a previous state, depending on whether a person or a policy was modified.
     */
    public void undo() {
        currentStatePointer--;
        resetData(statefulAddressBookList.get(currentStatePointer));
    }

    // TODO: Add redo exception.
    /**
     * Restores our address book to its previously undone state, depending on whether a person or policy was undone.
     */
    public void redo() {
        currentStatePointer++;
        resetData(statefulAddressBookList.get(currentStatePointer));
    }

    /**
     * Checks whether an undo is possible in the address book.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Checks whether a redo is possible in the address book.
     */
    public boolean canRedo() {
        return currentStatePointer < statefulAddressBookList.size() - 1;
    }

    @Override
    public boolean equals(Object obj) {
        // if the same object
        if (obj == this) {
            return true;
        }

        // if different kinds of objects
        if (!(obj instanceof StatefulAddressBook)) {
            return false;
        }

        StatefulAddressBook other = (StatefulAddressBook) obj;
        return super.equals(other)
                && statefulAddressBookList.equals(other.statefulAddressBookList)
                && currentStatePointer == other.currentStatePointer;
    }

}
