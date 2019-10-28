package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.NoSuchElementException;

import javafx.collections.ObservableList;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all Interviewee data at the IntervieweeList level
 * Duplicates are not allowed
 */
public class IntervieweeList implements ReadOnlyIntervieweeList {

    private final UniquePersonList<Interviewee> interviewees;

    public IntervieweeList() {
        this.interviewees = new UniquePersonList<>();
    }

    /**
     * Creates an IntervieweeList using the Interviewee list in the {@code toBeCopied}.
     */
    public IntervieweeList(ReadOnlyIntervieweeList toBeCopied) {
        this();
        this.resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the interviewee list with {@code interviewees}.
     * {@code interviewees} must not contain duplicate interviewees.
     */
    public void setInterviewees(List<Interviewee> interviewees) {
        this.interviewees.setPersons(interviewees);
    }

    /**
     * Resets the existing data of this {@code IntervieweeList} with {@code newData}.
     */
    public void resetData(ReadOnlyIntervieweeList newData) {
        requireNonNull(newData);

        this.setInterviewees(newData.getIntervieweeList());
    }

    /**
     * Returns true if an Interviewee with the same identity as {@code interviewee} exists in the database.
     */
    public boolean hasInterviewee(Interviewee interviewee) {
        requireNonNull(interviewee);
        return this.interviewees.contains(interviewee);
    }

    /**
     * Adds an Interviewee to the database.
     * The Interviewer must not already exist in the database.
     */
    public void addInterviewee(Interviewee interviewee) {
        this.interviewees.add(interviewee);
    }

    /**
     * Returns an Interviewee from the database given the name.
     */
    public Interviewee getInterviewee(String name) throws NoSuchElementException {
        requireNonNull(name);
        return this.interviewees.asUnmodifiableObservableList().stream()
                .filter((x) -> x.getName().equals(name))
                .findAny()
                .get();
    }

    /**
     * Replaces the given Interviewee {@code target} in the list with {@code editedInterviewee}.
     * {@code target} must exist in the database.
     * The Interviewee identity of {@code editedInterviewee} must not be the same as another existing
     * Interviewee in the address book.
     */
    public void setInterviewee(Interviewee target, Interviewee editedInterviewee) {
        requireNonNull(editedInterviewee);
        this.interviewees.setPerson(target, editedInterviewee);
    }

    /**
     * Removes {@code key} from this {@code IntervieweeList}.
     * {@code key} must exist in the database.
     */
    public void removeInterviewee(Interviewee key) {
        this.interviewees.remove(key);
    }

    @Override
    public String toString() {
        return this.getIntervieweeList().size() + " interviewees";
    }

    @Override
    public ObservableList<Interviewee> getIntervieweeList() {
        return this.interviewees.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IntervieweeList // instanceof handles nulls
                && this.interviewees.equals(((IntervieweeList) other).interviewees));
    }

    @Override
    public int hashCode() {
        return this.interviewees.hashCode();
    }

}
