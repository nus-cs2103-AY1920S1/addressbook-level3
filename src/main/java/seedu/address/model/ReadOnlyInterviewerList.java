package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Interviewer;

/**
 * Unmodifiable view of an list of Interviewers
 */
public interface ReadOnlyInterviewerList {

    /**
     * Returns an unmodifiable view of the Interviewer list.
     * This list will not contain any duplicate Interviewers.
     */
    ObservableList<Interviewer> getInterviewerList();

}
