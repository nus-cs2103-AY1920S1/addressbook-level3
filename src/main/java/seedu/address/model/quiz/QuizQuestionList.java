package seedu.address.model.quiz;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Question;

/**
 * A list of quiz questions.
 */
public class QuizQuestionList implements Iterable<Question> {
    private final ObservableList<Question> internalList = FXCollections.observableArrayList();
    private final ObservableList<Question> modifiableList = FXCollections.observableList(internalList);
    private final ObservableList<Question> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Sets the question list in quiz as {@code quizQuestionList}.
     */
    public void setQuizQuestionList(ObservableList<Question> quizQuestionList) {
        internalList.setAll(quizQuestionList);
    }

    public Question get(int index) {
        return internalList.get(index);
    }

    /**
     * Gets one question from the list and return a new list contains this question.
     */
    public ObservableList<Question> getOneQuizQuestionAsList() {
        ObservableList<Question> oneQuestionList = FXCollections.observableArrayList();
        if (modifiableList.size() > 0) {
            Question question = modifiableList.get(0);
            oneQuestionList.add(question);
        }
        return oneQuestionList;
    }

    /**
     * Gets one question from the list and return a question.
     */
    public Question getOneQuizQuestion() {
        return modifiableList.get(0);
    }

    /**
     * Removes one question from the first element of list.
     */
    public void removeOneQuizQuestion() {
        modifiableList.remove(0);
    }

    /**
     * Returns an answer for the question in quiz with specific {@code index}.
     */
    public Answer showAnswer() {
        return getOneQuizQuestion().getAnswer();
    }

    /**
     * Checks if the answer input by user is correct and return a boolean value to show the result.
     */
    public boolean checkQuizAnswer(Answer answer) {
        requireAllNonNull(answer);

        return getOneQuizQuestion().getAnswer().equals(answer);
    }

    /**
     * Clears the quiz question list.
     */
    public void clearQuizQuestionList() {
        internalList.clear();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Question> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Question> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuizQuestionList // instanceof handles nulls
                && internalList.equals(((QuizQuestionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
