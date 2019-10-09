package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.common.ReferenceId;
import seedu.address.model.person.UniqueReferenceIdList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class QueueList {
    private final UniqueReferenceIdList ids;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        ids = new UniqueReferenceIdList();
    }

    public QueueList() {

    }

    public QueueList(QueueList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code QueueList} with {@code newData}.
     */
    public void resetData(QueueList newData) {
        requireNonNull(newData);
        setIds(newData.getReferenceIdList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasId(ReferenceId id) {
        requireNonNull(id);
        return ids.contains(id);
    }

    /**
     * Adds a person to the queue
     * The person must not already exist in the queue.
     */
    public void addPatient(ReferenceId id) {
        ids.add(id);
    }

    public void setIds(List<ReferenceId> ids) {
        this.ids.setIds(ids);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePatient(ReferenceId id) {
        ids.remove(id);
    }

    public void removePatient(int index) {
        ids.remove(index);
    }

    public ReferenceId getFirst() {
        return ids.getFirst();
    }

    public void poll() {
        ids.remove(0);
    }

    public int size() {
        return ids.size();
    }

    //// util methods

    @Override
    public String toString() {
        return ids.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public int hashCode() {
        return ids.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QueueList // instanceof handles nulls
                && ids.equals(((QueueList) other).ids));
    }

    public ObservableList<ReferenceId> getReferenceIdList() {
        return ids.asUnmodifiableObservableList();
    }
}
