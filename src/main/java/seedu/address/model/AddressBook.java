package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.UniqueEateryList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEatery comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private boolean isMainMode = true;
    private final Logger logger = LogsCenter.getLogger(AddressBook.class);

    private final UniqueEateryList eateries;
    private final UniqueEateryList todo;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        eateries = new UniqueEateryList();
        todo = new UniqueEateryList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Eaterys in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the eatery list with {@code eateries}.
     * {@code eateries} must not contain duplicate eateries.
     */
    public void setEateries(List<Eatery> eateries, List<Eatery> todos) {
        this.eateries.setEateries(eateries);
        this.todo.setEateries(todos);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setEateries(newData.getEateryList(), newData.getTodoList());
    }

    //// eatery-level operations
    /**
     * Returns true if a eatery with the same identity as {@code eatery} exists in the address book.
     */
    public boolean hasEatery(Eatery eatery) {
        requireNonNull(eatery);
        if (isMainMode) {
            return eateries.contains(eatery);
        } else {
            return todo.contains(eatery);
        }
    }

    /**
     * Adds a eatery to the address book.
     * The eatery must not already exist in the address book.
     */
    public void addEatery(Eatery e) {
        if (isMainMode) {
            eateries.add(e);
        } else {
            todo.add(e);
        }
    }

    /**
     * Replaces the given eatery {@code target} in the list with {@code editedEatery}.
     * {@code target} must exist in the address book.
     * The eatery identity of {@code editedEatery} must not be the same as another existing eatery in the address book.
     */
    public void setEatery(Eatery target, Eatery editedEatery) {
        requireNonNull(editedEatery);
        if (isMainMode) {
            eateries.setEatery(target, editedEatery);
        } else {
            todo.setEatery(target, editedEatery);
        }
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEatery(Eatery key) {
        if (isMainMode) {
            eateries.remove(key);
        } else {
            todo.remove(key);
        }
    }

    //// util methods
    /**
     * Toggle between Main mode and To-do mode.
     */
    public void toggle() {
        isMainMode = !isMainMode;
    }

    public boolean isMainMode() {
        return isMainMode;
    }

    @Override
    public String toString() {
        return String.format("%d eateries: %s", eateries.asUnmodifiableObservableList().size(),
                eateries.asUnmodifiableObservableList());
    }

    @Override
    public ObservableList<Eatery> getEateryList() {
        return eateries.asUnmodifiableObservableList();
    }

    public ObservableList<Eatery> getTodoList() {
        return todo.asUnmodifiableObservableList();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && eateries.equals(((AddressBook) other).eateries));
    }

    @Override
    public int hashCode() {
        return eateries.hashCode();
    }

}
