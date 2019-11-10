package seedu.planner.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.planner.commons.core.index.Index;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.accommodation.UniqueAccommodationList;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Name;
//@@author OneArmyj
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
     * Returns true if an accommodation with the same identity as {@code accommodations} exists in plan2travel.
     */
    public boolean hasAccommodation(Accommodation accommodation) {
        requireNonNull(accommodation);
        return accommodations.contains(accommodation);
    }

    /**
     * Return the optional index of accommodation to find in {@code accommodations}. Returns empty optional if
     * not found.
     */
    public Optional<Index> findAccommodationIndex(Accommodation toFind) {
        return accommodations.indexOf(toFind);
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
     * Returns the accommodation that matches the specified name and address
     */
    public Optional<Accommodation> getAccommodation(Name name, Address address) {
        return accommodations.getAccommodation(name, address);
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
