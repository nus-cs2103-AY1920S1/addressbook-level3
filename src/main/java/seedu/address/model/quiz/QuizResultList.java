package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.exceptions.EmptyQuizResultListException;
import seedu.address.model.quiz.exceptions.FilterTypeNotFoundException;

/**
 * Represents a quiz result list.
 */
public class QuizResultList implements Iterable<QuizResult> {
    private final ObservableList<QuizResult> internalList = FXCollections.observableArrayList();
    private final ObservableList<QuizResult> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Sets the internal quiz result list in the {@code QuizResultList}.
     * @param replacement The quiz result list to set.
     */
    public void setQuizResults(List<QuizResult> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement);
    }

    /**
     * Adds a quiz result to the result list.
     * @param quizResult The quiz result to add.
     */
    public void add(QuizResult quizResult) {
        requireNonNull(quizResult);
        internalList.add(quizResult);
    }

    /**
     * Returns true if the list contains a quiz result which equals the given argument.
     * @param toCheck The the {@code quizResult} to check against.
     */
    public boolean contains(QuizResult toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<QuizResult> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns a list of subjects that exists in the quiz result list.
     * The subjects will not be duplicated.
     * @return A unique list of subjects.
     */
    public ObservableList<Subject> getUniqueSubjectList() {
        Set<Subject> uniqueSubjectList = internalList.stream()
                .map(QuizResult::getSubject).collect(Collectors.toSet());
        return FXCollections.observableArrayList(new ArrayList<>(uniqueSubjectList));
    }

    /**
     * Returns a list of difficulties that exists in the quiz result list.
     * The difficulties will not be duplicated.
     * @return A unique list of difficulties.
     */
    public ObservableList<Difficulty> getUniqueDifficultyList() {
        Set<Difficulty> uniqueDifficultyList = internalList.stream()
                .map(QuizResult::getDifficulty).collect(Collectors.toSet());
        return FXCollections.observableArrayList(new ArrayList<>(uniqueDifficultyList));
    }

    /**
     * Returns a list with quiz results filtered by {@code quizResultFilter}.
     * @param quizResultFilter The filter to be applied to the list.
     * @return quizResults The filtered quiz results.
     */
    public ObservableList<QuizResult> filterQuizResult(QuizResultFilter quizResultFilter)
            throws EmptyQuizResultListException {
        Stack<FilterType> filterType = quizResultFilter.getOperations();
        List<QuizResult> filteredQuizResults = internalList;
        if (internalList.isEmpty()) {
            throw new EmptyQuizResultListException();
        }
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
            case CORRECT:
                filteredQuizResults = filteredQuizResults
                        .stream()
                        .filter(quizResult -> quizResult.getResult() && quizResultFilter.getIsCorrectQns())
                        .collect(Collectors.toList());
                break;
            case INCORRECT:
                filteredQuizResults = filteredQuizResults
                        .stream()
                        .filter(quizResult -> !quizResult.getResult() && !quizResultFilter.getIsCorrectQns())
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
        return FXCollections.observableArrayList(filteredQuizResults);
    }

    /**
     * Gets all quiz results for a specified question.
     * @param qns The question to get quiz results of.
     * @return quizResults The quiz results for the question.
     */
    public ObservableList<QuizResult> getQnsReport(Question qns) {
        List<QuizResult> qnsReport = internalList.stream()
                .filter(quizResult -> quizResult.getQuestionBody().equals(qns.getQuestionBody())
                        && quizResult.getDifficulty().equals(qns.getDifficulty())
                        && quizResult.getSubject().equals(qns.getSubject()))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(qnsReport);
    }

    @Override
    public Iterator<QuizResult> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuizResultList // instanceof handles nulls
                && internalList.equals(((QuizResultList) other).internalList));
    }
}
