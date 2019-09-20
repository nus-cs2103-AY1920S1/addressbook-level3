package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.Problem.Problem;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAlgoBase {

    /**
     * Returns an unmodifiable view of the problems list.
     * This list will not contain any duplicate problems.
     */
    ObservableList<Problem> getProblemList();

}
