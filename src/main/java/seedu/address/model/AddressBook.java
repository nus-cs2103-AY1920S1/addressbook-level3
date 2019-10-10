package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.answerable.UniqueAnswerableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameAnswerable comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueAnswerableList answerables;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        answerables = new UniqueAnswerableList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Answerables in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the answerable list with {@code answerables}.
     * {@code answerables} must not contain duplicate answerables.
     */
    public void setAnswerables(List<Answerable> answerables) {
        this.answerables.setAnswerables(answerables);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setAnswerables(newData.getAnswerableList());
    }

    //// answerable-level operations

    /**
     * Returns true if a answerable with the same identity as {@code answerable} exists in the address book.
     */
    public boolean hasAnswerable(Answerable answerable) {
        requireNonNull(answerable);
        return answerables.contains(answerable);
    }

    /**
     * Adds a answerable to the address book.
     * The answerable must not already exist in the address book.
     */
    public void addAnswerable(Answerable p) {
        answerables.add(p);
    }

    /**
     * Replaces the given answerable {@code target} in the list with {@code editedAnswerable}.
     * {@code target} must exist in the address book.
     * The answerable identity of {@code editedAnswerable} must not be the same as another existing answerable in the address book.
     */
    public void setAnswerable(Answerable target, Answerable editedAnswerable) {
        requireNonNull(editedAnswerable);

        answerables.setAnswerable(target, editedAnswerable);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeAnswerable(Answerable key) {
        answerables.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return answerables.asUnmodifiableObservableList().size() + " answerables";
        // TODO: refine later
    }

    @Override
    public ObservableList<Answerable> getAnswerableList() {
        return answerables.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && answerables.equals(((AddressBook) other).answerables));
    }

    @Override
    public int hashCode() {
        return answerables.hashCode();
    }
}
