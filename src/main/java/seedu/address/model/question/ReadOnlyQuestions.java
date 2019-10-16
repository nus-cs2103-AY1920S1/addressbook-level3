package seedu.address.model.question;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an student record
 */
public interface ReadOnlyQuestions {

    /**
     * Returns an unmodifiable view of the questions list. This list will not contain any duplicate
     * questions.
     */
    ObservableList<Question> getSavedQuestions();

}
