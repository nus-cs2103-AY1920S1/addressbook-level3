package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a quiz result list.
 */
public class QuizResultList implements Iterable<QuizResult> {
    private final ObservableList<QuizResult> internalList = FXCollections.observableArrayList();
    private final ObservableList<QuizResult> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final List<TempQnsModel> correctQns = new ArrayList<>(); //will be changed to <Question> later
    private final List<TempQnsModel> incorrectQns = new ArrayList<>(); // same as above
    private int totalQuestionsCorrect = 0;
    private int totalQuestionsIncorrect = 0;

    /**
     * Adds a quiz result to the result list. The result must be different from all existing ones.
     */
    public void add(QuizResult quizResult) {
        requireNonNull(quizResult);
        if (quizResult.getResult()) {
            totalQuestionsCorrect++;
            correctQns.add(new TempQnsModel(quizResult.getQuestionBody(), quizResult.getAnswer()));
        } else {
            totalQuestionsIncorrect++;
            incorrectQns.add(new TempQnsModel(quizResult.getQuestionBody(), quizResult.getAnswer()));
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
        return internalUnmodifiableList;
    }

    public int getTotalQuestionsDone() {
        return internalUnmodifiableList.size();
    }

    public int getTotalQuestionsCorrect() {
        return totalQuestionsCorrect;
    }

    public int getTotalQuestionsIncorrect() {
        return totalQuestionsIncorrect;
    }

    public List<TempQnsModel> getCorrectQns() {
        return correctQns;
    }

    public List<TempQnsModel> getIncorrectQns() {
        return incorrectQns;
    }

    @Override
    public Iterator<QuizResult> iterator() {
        return internalList.iterator();
    }
}
