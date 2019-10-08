package seedu.algobase.model;

import javafx.collections.ObservableList;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;

/**
 * Unmodifiable view of an algobase
 */
public interface ReadOnlyAlgoBase {

    /**
     * Returns an unmodifiable view of the problems list.
     * This list will not contain any duplicate problems.
     */
    ObservableList<Problem> getProblemList();
    ObservableList<Tag> getTagList();
}
