package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.answerable.Answerable;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the answerables list.
     * This list will not contain any duplicate answerables.
     */
    ObservableList<Answerable> getAnswerableList();

}
