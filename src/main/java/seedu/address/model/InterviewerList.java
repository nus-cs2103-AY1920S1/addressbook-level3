package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.NoSuchElementException;

import javafx.collections.ObservableList;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all Interviewer data at the InterviewerList level
 * Duplicates are not allowed
 */
public class InterviewerList implements ReadOnlyInterviewerList {

    private final UniquePersonList<Interviewer> interviewers;

    public InterviewerList() {
        this.interviewers = new UniquePersonList<>();
    }

    /**
     * Creates an InterviewerList using the Interviewer list in the {@code toBeCopied}
     */
    public InterviewerList(ReadOnlyInterviewerList toBeCopied) {
        this();
        this.resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the interviewer list with {@code interviewers}.
     * {@code interviewers} must not contain duplicate interviewers.
     */
    public void setInterviewers(List<Interviewer> interviewers) {
        this.interviewers.setPersons(interviewers);
    }

    /**
     * Resets the existing data of this {@code InterviewerList} with {@code newData}.
     */
    public void resetData(ReadOnlyInterviewerList newData) {
        requireNonNull(newData);

        this.setInterviewers(newData.getInterviewerList());
    }

    /**
     * Returns true if an Interviewer with the same identity as {@code interviewer} exists in the database.
     */
    public boolean hasInterviewer(Interviewer interviewer) {
        requireNonNull(interviewer);
        return this.interviewers.contains(interviewer);
    }

    /**
     * Adds an Interviewer to the database.
     * The Interviewer must not already exist in the database.
     */
    public void addInterviewer(Interviewer interviewer) {
        this.interviewers.add(interviewer);
    }

    /**
     * Returns an Interviewer from the database given the name.
     */
    public Interviewer getInterviewer(String name) throws NoSuchElementException {
        requireNonNull(name);
        return this.interviewers.asUnmodifiableObservableList().stream()
                .filter((x) -> x.getName().equals(name))
                .findAny()
                .get();
    }

    /**
     * Replaces the given Interviewer {@code target} in the list with {@code editedInterviewer}.
     * {@code target} must exist in the database.
     * The Interviewer identity of {@code editedInterviewer} must not be the same as another existing
     * Interviewer in the address book.
     */
    public void setInterviewer(Interviewer target, Interviewer editedInterviewer) {
        requireNonNull(editedInterviewer);
        this.interviewers.setPerson(target, editedInterviewer);
    }

    /**
     * Removes {@code key} from this {@code InterviewerList}.
     * {@code key} must exist in the database.
     */
    public void removeInterviewer(Interviewer key) {
        this.interviewers.remove(key);
    }

    @Override
    public String toString() {
        return this.getInterviewerList().size() + " interviewers";
    }

    @Override
    public ObservableList<Interviewer> getInterviewerList() {
        return this.interviewers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewerList // instanceof handles nulls
                && this.interviewers.equals(((InterviewerList) other).interviewers));
    }

    @Override
    public int hashCode() {
        return this.interviewers.hashCode();
    }

}
