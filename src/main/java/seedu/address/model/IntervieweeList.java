package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Name;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Wraps all interviewee data at the address-book level. Duplicates are not allowed (by Person#isSamePerson).
 */
public class IntervieweeList implements ReadAndWriteList<Interviewee> {

    private final UniquePersonList<Interviewee> interviewees;

    public IntervieweeList() {
        this.interviewees = new UniquePersonList<>();
    }

    public IntervieweeList(ReadOnlyList<Interviewee> book) {
        this();
        resetData(book);
    }

    /**
     * Adds the interviewee to the book. The interviewee must be unique.
     * @throws DuplicatePersonException if the interviewee already exists in the book.
     */
    @Override
    public void addEntity(Interviewee interviewee) throws DuplicatePersonException {
        interviewees.add(interviewee);
    }

    @Override
    public Interviewee getEntity(Name name) throws PersonNotFoundException {
        requireNonNull(name);
        Optional<Interviewee> i = interviewees.asUnmodifiableObservableList().stream()
                .filter(interviewee -> interviewee.getName().equals(name))
                .findAny();
        if (!i.isPresent()) {
            throw new PersonNotFoundException();
        }
        return i.get();
    }

    @Override
    public void setEntity(Interviewee target, Interviewee editedTarget) throws PersonNotFoundException {
        requireAllNonNull(target, editedTarget);
        // TODO: Implementation
        throw new RuntimeException("method not implemented yet");
    }

    @Override
    public void removeEntity(Interviewee interviewee) throws PersonNotFoundException {
        interviewees.remove(interviewee);
    }

    @Override
    public boolean hasEntity(Interviewee target) {
        return interviewees.contains(target);
    }

    @Override
    public ObservableList<Interviewee> getEntityList() {
        return interviewees.asUnmodifiableObservableList();
    }


    /**
     * Replaces the contents of the interviewee list with {@code interviewees}.
     * {@code interviewees} must not contain duplicate interviewees.
     */
    public void setIntervieweeList(List<Interviewee> interviewees) {
        this.interviewees.setPersons(interviewees);
    }

    /**
     * Resets the underlying {@code UniquePersonList<Interviewee>} with that of the {@code book}.
     */
    private void resetData(ReadOnlyList<Interviewee> book) {
        requireNonNull(book);
        this.interviewees.setPersons(book.getEntityList());
    }

    /**
     * Clears the allocated slot of all the interviewees
     */
    public void clearAllAllocatedSlots() {
        for (Interviewee interviewee : interviewees) {
            interviewee.clearAllocatedSlot();
        }
    }

    @Override
    public String toString() {
        return interviewees.asUnmodifiableObservableList().size() + " interviewees";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IntervieweeList// instanceof handles nulls
                && interviewees.equals(((IntervieweeList) other).interviewees));
    }

    @Override
    public int hashCode() {
        return interviewees.hashCode();
    }
}
