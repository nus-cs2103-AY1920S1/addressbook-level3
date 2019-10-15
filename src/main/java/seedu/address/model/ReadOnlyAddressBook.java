package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.note.Note;
import seedu.address.model.question.Question;

/**
 * Unmodifiable view of a lecture note list and a question list.
 */
public interface ReadOnlyAddressBook {
    /**
     * Returns an unmodifiable view of the lecture note list.
     * This list will not contain any duplicate titles.
     */
    ObservableList<Note> getNoteList();

    /**
     * Returns an unmodifiable view of the questions list.
     * This list will not contain any duplicate questions.
     */
    ObservableList<Question> getQuestionList();
}
