package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Interviewee;

/**
 * Unmodifiable view of an list of IntervieweeList
 */
public interface ReadOnlyIntervieweeList {

    /**
     * Returns an unmodifiable view of the Interviewee list.
     * This list will not contain any duplicate IntervieweeList.
     */
    ObservableList<Interviewee> getIntervieweeList();

}
