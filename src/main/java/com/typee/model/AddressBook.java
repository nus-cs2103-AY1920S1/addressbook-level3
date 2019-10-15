package com.typee.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.typee.model.engagement.Engagement;
import com.typee.model.person.Person;
import com.typee.model.person.UniqueEngagementList;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEngagementList engagements;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        engagements = new UniqueEngagementList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEngagements(List<Engagement> engagements) {
        this.engagements.setEngagements(engagements);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setEngagements(newData.getEngagementList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasEngagement(Engagement engagement) {
        requireNonNull(engagement);
        return engagements.contains(engagement);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addEngagement(Engagement engagement) {
        engagements.add(engagement);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Engagement target, Engagement editedEngagement) {
        requireNonNull(editedEngagement);

        engagements.setEngagement(target, editedEngagement);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEngagement(Engagement key) {
        engagements.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return engagements.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Engagement> getEngagementList() {
        return engagements.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && engagements.equals(((AddressBook) other).engagements));
    }

    @Override
    public int hashCode() {
        return engagements.hashCode();
    }
}
