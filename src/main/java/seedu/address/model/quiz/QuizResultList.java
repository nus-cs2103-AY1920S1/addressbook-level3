package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.statistics.TempStatsQnsModel;

/**
 * Represents a quiz result list.
 */
public class QuizResultList implements Iterable<QuizResult> {
    private final ObservableList<QuizResult> internalList = FXCollections.observableArrayList();
    private final ObservableList<QuizResult> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    //will be changed to <Question> later
    private final ObservableList<TempStatsQnsModel> correctQns = FXCollections.observableArrayList();
    private final ObservableList<TempStatsQnsModel> incorrectQns = FXCollections.observableArrayList();

    private int totalQuestionsCorrect = 0;
    private int totalQuestionsIncorrect = 0;

    /**
     * Adds a quiz result to the result list. The result must be different from all existing ones.
     */
    public void add(QuizResult quizResult) {
        requireNonNull(quizResult);
        if (quizResult.getResult()) {
            totalQuestionsCorrect++;
            correctQns.add(new TempStatsQnsModel(quizResult.getQuestionBody(), quizResult.getAnswer()));
        } else {
            totalQuestionsIncorrect++;
            incorrectQns.add(new TempStatsQnsModel(quizResult.getQuestionBody(), quizResult.getAnswer()));
        }
        internalList.add(quizResult);
    }

    /**
     * Returns true if the list contains a quiz result which equals with the given argument.
     */
    public boolean contains(QuizResult toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    public QuizResult get(int index) {
        return internalList.get(index);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<QuizResult> asUnmodifiableObservableList() {
        return internalList;
    }

    public int getTotalQuestionsDone() {
        return internalList.size();
    }

    public int getTotalQuestionsCorrect() {
        return totalQuestionsCorrect;
    }

    public int getTotalQuestionsIncorrect() {
        return totalQuestionsIncorrect;
    }

    public ObservableList<TempStatsQnsModel> getCorrectQns() {
        return FXCollections.unmodifiableObservableList(correctQns);
    }

    public ObservableList<TempStatsQnsModel> getIncorrectQns() {
        return FXCollections.unmodifiableObservableList(incorrectQns);
    }

    @Override
    public Iterator<QuizResult> iterator() {
        return internalList.iterator();
    }
}
