package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.quiz.exceptions.FilterTypeNotFoundException;
import seedu.address.model.statistics.TempStatsQnsModel;

//import java.util.Date;

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

    public void setQuizResults(List<QuizResult> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement);
    }

    /**
     * Adds a quiz result to the result list. The result must be different from all existing ones.
     */
    public void add(QuizResult quizResult) {
        requireNonNull(quizResult);
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

    public ObservableList<TempStatsQnsModel> getCorrectQns() {
        return FXCollections.unmodifiableObservableList(correctQns);
    }

    public ObservableList<TempStatsQnsModel> getIncorrectQns() {
        return FXCollections.unmodifiableObservableList(incorrectQns);
    }

    /**
     * Returns a list with quizResults filtered by {@code subject}, {@code difficulty}, {@code start}
     * and {@code end}.
     */
    public ObservableList<QuizResult> filterQuizResult(QuizResultFilter quizResultFilter) {
        Stack<FilterType> filterType = quizResultFilter.getOperations();
        List<QuizResult> filteredQuizResults = internalList;
        while (!filterType.empty()) {
            switch(filterType.pop()) {
            case NONE:
                break;
            case SUBJECT:
                filteredQuizResults = filteredQuizResults
                        .stream()
                        .filter(quizResult -> quizResultFilter.getSubjects()
                                .stream()
                                .anyMatch(subject -> subject.equals(quizResult.getSubject())))
                        .collect(Collectors.toList());
                break;
            case DIFFICULTY:
                filteredQuizResults = filteredQuizResults
                        .stream()
                        .filter(quizResult -> quizResult.getDifficulty().equals(quizResultFilter.getDifficulty()))
                        .collect(Collectors.toList());
                break;
            case DATE:
                filteredQuizResults = filteredQuizResults
                        .stream()
                        .filter(quizResult -> quizResult.isWithinDate(quizResultFilter.getStartDate(),
                                quizResultFilter.getEndDate()))
                        .collect(Collectors.toList());
                break;
            default:
                throw new FilterTypeNotFoundException();
            }
        }
        ObservableList<QuizResult> quizResults = FXCollections.observableArrayList(filteredQuizResults);

        return quizResults;
    }

    @Override
    public Iterator<QuizResult> iterator() {
        return internalList.iterator();
    }
}
