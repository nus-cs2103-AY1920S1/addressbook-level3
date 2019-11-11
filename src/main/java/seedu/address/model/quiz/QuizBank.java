package seedu.address.model.quiz;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a storage class which holds all the quizzes created up to date.
 */
public class QuizBank implements Iterable<Quiz> {

    private static String currentlyQueriedQuiz;

    private final ObservableList<Quiz> quizzes = FXCollections.observableArrayList();
    private final ObservableList<Quiz> quizzesUnmodifiableList =
            FXCollections.unmodifiableObservableList(quizzes);

    /**
     * Replaces the contents of this list with {@code Quizzes}. {@code Quizzes} must not contain
     * duplicate Quizzes.
     */
    public void setQuizzes(List<Quiz> quizzes) {
        requireAllNonNull(quizzes);
        this.quizzes.setAll(quizzes);
    }

    /**
     * Adds a quiz to a quiz bank.
     * @param quiz The quiz to be added to the quiz bank.
     */
    public void addQuiz(Quiz quiz) {
        if (!isRepeated(quiz)) {
            quizzes.add(quiz);
        }
    }

    /**
     * Removes a quiz from a quiz bank.
     * @param quizId The quiz to be removed from the quiz bank.
     */
    public void removeQuiz(String quizId) {
        for (Quiz q : quizzes) {
            if (q.getQuizId().equals(quizId)) {
                quizzes.remove(q);
            }
        }
    }

    /**
     * Returns the quizIndex of a quiz if found, else -1.
     * @param quizId The quiz identifier of the quiz.
     * @return The quizIndex of the quiz.
     */
    public int getQuizIndex(String quizId) {
        for (int i = 0; i < quizzes.size(); i++) {
            Quiz currentQuiz = quizzes.get(i);
            if (currentQuiz.getQuizId().equals(quizId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a quiz from a quiz bank.
     * @param quizIndex The quiz index.
     * @return The quiz from the quiz bank.
     */
    public Quiz getQuiz(int quizIndex) {
        return quizzes.get(quizIndex);
    }

    /**
     * Sets the currently queried quiz.
     */
    public static void setCurrentlyQueriedQuiz(String queriedQuiz) {
        currentlyQueriedQuiz = queriedQuiz;
    }

    /**
     * Gets the currently queried quiz.
     * @return the currently queried quiz.
     */
    public static String getCurrentlyQueriedQuiz() {
        return currentlyQueriedQuiz;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Quiz> asUnmodifiableObservableList() {
        return quizzesUnmodifiableList;
    }

    @Override
    public Iterator<Quiz> iterator() {
        return quizzes.iterator();
    }

    /**
     * Checks if a quiz has been repeated, i.e same quizIds.
     * @param quiz The quiz to be checked.
     * @return True if the quiz is repeated, else false.
     */
    private boolean isRepeated(Quiz quiz) {
        for (Quiz q : quizzes) {
            String thisQuizId = q.getQuizId();
            String otherQuizId = quiz.getQuizId();
            if (thisQuizId.equals(otherQuizId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof QuizBank)) {
            return false;
        }

        QuizBank otherQuizBank = (QuizBank) other;
        return otherQuizBank.quizzes.equals(this.quizzes);
    }
}
