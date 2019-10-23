package seedu.address.model;

import java.util.ArrayList;

/**
 * Manages a list of all the previous Address Books.
 */
public class Caretaker extends AddressBook {

    private ArrayList<Memento> mementos = new ArrayList<>();
    private int statePointer;
    private final AddressBook addressBook;

    /**
     * Initializes the list of mementos.
     * @param start the first state of the address book.
     * @param addressBook the address book where changes are updated in
     */
    public Caretaker(Memento start, AddressBook addressBook) {
        statePointer = 0;
        mementos.add(start);
        this.addressBook = addressBook;
    }

    /**
     * Saves the current state of address book into the list of mementos.
     */
    public void saveState() {
        mementos = new ArrayList<>(mementos.subList(0, statePointer + 1));
        Memento mementoToAdd = new Memento(new AddressBook(this.addressBook));
        mementos.add(mementoToAdd);
        statePointer++;
    }

    /**
     * Undoes the previous command.
     * @return ReadOnlyAddressBook that is the previous state of the address book.
     */
    public ReadOnlyAddressBook undo() {
        statePointer--;
        ReadOnlyAddressBook previousCopy = mementos.get(statePointer).getState();
        resetData(previousCopy);
        return previousCopy;
    }

    /**
     * Redoes the previously undone command.
     * @return ReadOnlyAddressBook that is the previous state of the address book.
     */
    public ReadOnlyAddressBook redo() {
        statePointer++;
        ReadOnlyAddressBook previousCopy = mementos.get(statePointer).getState();
        resetData(previousCopy);
        return previousCopy;
    }

    /**
     * Checks if there is anything to undo.
     * @return true if there are commands to undo.
     */
    public boolean canUndo() {
        return statePointer > 0;
    }

    /**
     * Checks if there is anything to redo.
     * @return true if there are commands to redo.
     */
    public boolean canRedo() {
        return statePointer < (mementos.size() - 1);
    }
}
