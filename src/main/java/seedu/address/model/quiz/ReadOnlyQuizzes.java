package seedu.address.model.quiz;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an student record
 */
public interface ReadOnlyQuizzes {

    /**
     * Returns an unmodifiable view of the questions list. This list will not contain any duplicate
     * questions.
     */
    ObservableList<Quiz> getSavedQuizzes();

}
