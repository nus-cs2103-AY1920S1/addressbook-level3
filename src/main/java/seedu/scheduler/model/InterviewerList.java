package seedu.scheduler.model;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.UniquePersonList;
import seedu.scheduler.model.person.exceptions.DuplicatePersonException;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;

/**
 * Wraps all interviewee data in the model.
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class InterviewerList implements ReadAndWriteList<Interviewer> {

    private final UniquePersonList<Interviewer> interviewers;

    public InterviewerList() {
        this.interviewers = new UniquePersonList<>();
    }

    public InterviewerList(ReadOnlyList<Interviewer> book) {
        this();
        resetData(book);
    }

    /**
     * Adds the interviewer to the book. The interviewer must be unique.
     * @throws DuplicatePersonException if the interviewer already exists in the book.
     */
    @Override
    public void addEntity(Interviewer interviewer) throws DuplicatePersonException {
        interviewers.add(interviewer);
    }

    @Override
    public void setEntity(Interviewer target, Interviewer editedTarget) throws PersonNotFoundException {
        requireAllNonNull(target, editedTarget);
        interviewers.setPerson(target, editedTarget);
    }

    @Override
    public Interviewer getEntity(Name name) throws PersonNotFoundException {
        requireNonNull(name);
        Optional<Interviewer> i = interviewers.asUnmodifiableObservableList().stream()
                .filter(interviewer -> interviewer.getName().equals(name))
                .findAny();
        if (!i.isPresent()) {
            throw new PersonNotFoundException();
        }
        return i.get();
    }

    @Override
    public void removeEntity(Interviewer interviewer) throws PersonNotFoundException {
        interviewers.remove(interviewer);
    }

    @Override
    public boolean hasEntity(Interviewer target) {
        return interviewers.contains(target);
    }

    @Override
    public ObservableList<Interviewer> getEntityList() {
        return interviewers.asUnmodifiableObservableList();
    }

    /**
     * Replaces the contents of the interviewer list with {@code interviewers}.
     * {@code interviewers} must not contain duplicate interviewers.
     */
    public void setInterviewerList(List<Interviewer> interviewers) {
        this.interviewers.setPersons(interviewers);
    }

    /**
     * Resets the underlying {@code UniquePersonList<Interviewer>} with that of the {@code book}.
     */
    private void resetData(ReadOnlyList<Interviewer> book) {
        requireNonNull(book);
        this.interviewers.setPersons(book.getEntityList());
    }

    /**
     * Clears the allocated slots of all interviewers.
     */
    public void clearAllAllocatedSlots() {
        this.interviewers.forEach(Interviewer::clearAllocatedSlots);
    }

    /**
     * Returns a string of interviewers in the format "DEPARTMENT - NAME" to be the headers for a schedule object.
     * @return headers
     */
    public ArrayList<String> getTitlesForSchedule() {
        ArrayList<String> titles = new ArrayList<>();
        for (Interviewer interviewer: interviewers) {
            Name name = interviewer.getName();
            Department department = interviewer.getDepartment();
            titles.add(stringifyHeadersForTable(name, department));
        }
        return titles;
    }

    /**
     * Static method to combine name and department into one string used for headers.
     * @param name
     * @param department
     * @return Header as String
     */
    public static String stringifyHeadersForTable(Name name, Department department) {
        return department.toString() + " - " + name.toString();
    }

    @Override
    public String toString() {
        return interviewers.asUnmodifiableObservableList().size() + " interviewers";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewerList// instanceof handles nulls
                && interviewers.equals(((InterviewerList) other).interviewers));
    }

    @Override
    public int hashCode() {
        return interviewers.hashCode();
    }
}
