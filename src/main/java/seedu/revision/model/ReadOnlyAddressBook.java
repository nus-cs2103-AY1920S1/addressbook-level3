package seedu.revision.model;

import javafx.collections.ObservableList;
import seedu.revision.model.answerable.Answerable;

/**
 * Unmodifiable view of an revision tool.
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the answerables list.
     * This list will not contain any duplicate answerables.
     */
    ObservableList<Answerable> getAnswerableList();

}
