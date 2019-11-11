package seedu.address.model.quiz;

import javafx.collections.ObservableList;
import seedu.address.model.quiz.person.Question;

/**
 * Unmodifiable view of modulo
 */
public interface ReadOnlyQuizBook {

    /**
     * Returns an unmodifiable view of the questions list.
     * This list will not contain any duplicate questions.
     */
    ObservableList<Question> getQuestionList();

    /**
     * Returns an unmodifiable view of a question.
     */
    ObservableList<Question> getShowQuestionList();
}
