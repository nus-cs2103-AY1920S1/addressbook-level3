package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.accommodation.UniqueAccommodationList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class AccommodationManager implements ReadOnlyAccommodation {
    private final UniqueAccommodationList accommodations;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        accommodations = new UniqueAccommodationList();
    }

    public AccommodationManager() {}

    /**
     * Creates an AccommodationManager using the Persons in the {@code toBeCopied}
     */
    public AccommodationManager(ReadOnlyAccommodation toBeCopied) {
        this();
        resetDataAccommodation(toBeCopied);
    }

    //// For ACCOMMODATION list overwrite operations

    /**
     * Resets the existing data of this {@code AccommodationManager} with {@code newData}.
     */
    public void resetDataAccommodation(ReadOnlyAccommodation newData) {
        requireNonNull(newData);

        setAccommodations(newData.getAccommodationList());
    }

    /**
     * Replaces the contents of the contacts list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations.setAccommodations(accommodations);
    }

    //// accommodation-level operations

    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    public boolean hasAccommodation(Accommodation accommodation) {
        requireNonNull(accommodation);
        return accommodations.contains(accommodation);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addAccommodation(Accommodation a) {
        accommodations.add(a);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addAccommodationAtIndex(Index index, Accommodation a) {
        accommodations.addAtIndex(index, a);
    }

    /**
     * Replaces the given contacts {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedPerson} must not be the same as another existing contacts in the
     * address book.
     */
    public void setAccommodation(Accommodation target, Accommodation editedAccommodation) {
        requireNonNull(editedAccommodation);

        accommodations.setAccommodation(target, editedAccommodation);
    }

    /**
     * Removes {@code key} from this {@code AccommodationManager}.
     * {@code key} must exist in the address book.
     */
    public void removeAccommodation(Accommodation key) {
        accommodations.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return accommodations.asUnmodifiableObservableList().size() + " accommodations,";
    }

    @Override
    public ObservableList<Accommodation> getAccommodationList() {
        return accommodations.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccommodationManager // instanceof handles nulls
                && accommodations.equals(((AccommodationManager) other).accommodations));
    }

    @Override
    public int hashCode() {
        return accommodations.hashCode();
    }
}
