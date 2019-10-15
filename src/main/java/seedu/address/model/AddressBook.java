package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.UniqueEateryList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEatery comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEateryList eateries;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        eateries = new UniqueEateryList();
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
    public void setEateries(List<Eatery> eateries) {
        this.eateries.setEateries(eateries);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setEateries(newData.getEateryList());
    }

    //// eatery-level operations

    /**
     * Returns true if a eatery with the same identity as {@code eatery} exists in the address book.
     */
    public boolean hasEatery(Eatery eatery) {
        requireNonNull(eatery);
        return eateries.contains(eatery);
    }

    /**
     * Adds a eatery to the address book.
     * The eatery must not already exist in the address book.
     */
    public void addEatery(Eatery e) {
        eateries.add(e);
    }

    /**
     * Replaces the given eatery {@code target} in the list with {@code editedEatery}.
     * {@code target} must exist in the address book.
     * The eatery identity of {@code editedEatery} must not be the same as another existing eatery in the address book.
     */
    public void setEatery(Eatery target, Eatery editedEatery) {
        requireNonNull(editedEatery);

        eateries.setEatery(target, editedEatery);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEatery(Eatery key) {
        eateries.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return eateries.asUnmodifiableObservableList().size() + " eateries";
        // TODO: refine later
    }

    @Override
    public ObservableList<Eatery> getEateryList() {
        return eateries.asUnmodifiableObservableList();
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
