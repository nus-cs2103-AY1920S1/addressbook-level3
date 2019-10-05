package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.UniqueBodyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameBody comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueBodyList bodies;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        bodies = new UniqueBodyList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Bodies in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the body list with {@code bodies}.
     * {@code bodies} must not contain duplicate bodies.
     */
    public void setBodies(List<Body> bodies) {
        this.bodies.setBodies(bodies);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setBodies(newData.getBodyList());
    }

    //// body-level operations

    /**
     * Returns true if a body with the same identity as {@code body} exists in the address book.
     */
    public boolean hasBody(Body body) {
        requireNonNull(body);
        return bodies.contains(body);
    }

    /**
     * Adds a body to the address book.
     * The body must not already exist in the address book.
     */
    public void addBody(Body p) {
        bodies.add(p);
    }

    /**
     * Replaces the given body {@code target} in the list with {@code editedBody}.
     * {@code target} must exist in the address book.
     * The body identity of {@code editedBody} must not be the same as another existing body in the address book.
     */
    public void setBody(Body target, Body editedBody) {
        requireNonNull(editedBody);

        bodies.setBody(target, editedBody);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeBody(Body key) {
        bodies.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return bodies.asUnmodifiableObservableList().size() + " bodies";
        // TODO: refine later
    }

    @Override
    public ObservableList<Body> getBodyList() {
        return bodies.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && bodies.equals(((AddressBook) other).bodies));
    }

    @Override
    public int hashCode() {
        return bodies.hashCode();
    }
}
