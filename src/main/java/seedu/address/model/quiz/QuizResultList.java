package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
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

    /**
     * Returns a list of subjects that exists in the quizResults list.
     * The subjects will not be duplicated.
     * @return A unique list of subejects.
     */
    public ObservableList<Subject> getUniqueSubjectList() {
        List<Subject> subjectsList = internalList.stream()
                .map(quizResult -> quizResult.getSubject())
                .collect(Collectors.toList());
        Set<Subject> uniqueSubjectList = new HashSet<Subject>(subjectsList);
        return FXCollections.observableArrayList(new ArrayList<>(uniqueSubjectList));
    }

    /**
     * Returns a list of difficulties that exists in the quizResults list.
     * The difficulties will not be duplicated.
     * @return A unique list of difficulties.
     */
    public ObservableList<Difficulty> getUniqueDifficultyList() {
        List<Difficulty> difficultyList = internalList.stream()
                .map(quizResult -> quizResult.getDifficulty())
                .collect(Collectors.toList());
        Set<Difficulty> uniqueDifficultyList = new HashSet<Difficulty>(difficultyList);
        return FXCollections.observableArrayList(new ArrayList<>(uniqueDifficultyList));
    }

    /**
     * Returns a list with quizResults filtered by {@code quizResultFilter}.
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
        ObservableList<QuizResult> quizResults = FXCollections.observableArrayList(filteredQuizResults);

        return quizResults;
    }

    public ObservableList<QuizResult> getQnsReport(Question qns) {
        List<QuizResult> qnsReport = internalList;
        qnsReport = internalList
                .stream()
                .filter(quizResult -> quizResult.getQuestionBody().equals(qns.getQuestionBody())
                    && quizResult.getDifficulty().equals(qns.getDifficulty())
                    && quizResult.getSubject().equals(qns.getSubject()))
                .collect(Collectors.toList());
        ObservableList<QuizResult> quizResults = FXCollections.observableArrayList(qnsReport);
        return quizResults;
    }

    @Override
    public Iterator<QuizResult> iterator() {
        return internalList.iterator();
    }
}
