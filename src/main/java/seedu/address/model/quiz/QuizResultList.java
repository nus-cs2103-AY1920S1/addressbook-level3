package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.quiz.FilterType.DATE;
import static seedu.address.model.quiz.FilterType.DIFFICULTY;
import static seedu.address.model.quiz.FilterType.SUBJECT;
import static seedu.address.model.quiz.FilterType.SUBJECT_AND_DIFFICULTY;
import static seedu.address.model.quiz.FilterType.SUBJECT_AND_QUESTION;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
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
        return internalUnmodifiableList;
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

    /**
     * Returns a list with quizResults filtered by {@code subject}, {@code difficulty}, {@code start}
     * and {@code end}.
     */
    public ObservableList<QuizResult> filterQuizResult(QuizResultFilter quizResultFilter) {
        FilterType filterType = quizResultFilter.getFilterType();
        List<QuizResult> filteredQuizResults = internalList;
        switch (filterType) {
        case SUBJECT:
            filteredQuizResults = filterBySubject(quizResultFilter);
            break;
        case SUBJECT_AND_CORRECT_QUESTION:
            filteredQuizResults = filterBySubjectAndCorrectQns(quizResultFilter);
            break;
        case SUBJECT_AND_WRONG_QUESTION:
            filteredQuizResults = filterBySubjectAndIncorrectQns(quizResultFilter);
            break;
        case SUBJECT_AND_DIFFICULTY:
            filteredQuizResults = filterBySubjectAndDifficulty(quizResultFilter);
            break;
        case DIFFICULTY:
            filteredQuizResults = filterByDifficulty(quizResultFilter);
            break;
        case DATE:
            filteredQuizResults = filterByDate(quizResultFilter);
            break;
        default:
            // throw new exception
        }
        ObservableList<QuizResult> quizResults = FXCollections.observableArrayList(filteredQuizResults);

        return quizResults;
    }

    private List<QuizResult> filterBySubject(QuizResultFilter quizResultFilter) {
        List<QuizResult> filteredQuizResults = internalList
                .stream()
                .flatMap(quizResult -> quizResultFilter.getSubjects()
                        .stream()
                        .filter(subject -> subject.equals(quizResult.getSubject())))
                .collect(Collectors.toList());
        return filteredQuizResults;
    }

    private List<QuizResult> filterBySubjectAndCorrectQns(QuizResultFilter quizResultFilter) {
        List<QuizResult> filteredQuizResults = internalList
                .stream()
                .flatMap(quizResult -> quizResultFilter.getSubjects()
                        .stream()
                        .filter(subject -> subject.equals(quizResult.getSubject())
                                && quizResult.getResult()))
                .collect(Collectors.toList());
        return filteredQuizResults;
    }

    private List<QuizResult> filterBySubjectAndIncorrectQns(QuizResultFilter quizResultFilter) {
        List<QuizResult> filteredQuizResults = internalList
                .stream()
                .flatMap(quizResult -> quizResultFilter.getSubjects()
                        .stream()
                        .filter(subject -> subject.equals(quizResult.getSubject())
                                && !quizResult.getResult()))
                .collect(Collectors.toList());
        return filteredQuizResults;
    }

    private List<QuizResult> filterBySubjectAndDifficulty(QuizResultFilter quizResultFilter) {
        List<QuizResult> filteredQuizResults = internalList
                .stream()
                .flatMap(quizResult -> quizResultFilter.getSubjects()
                        .stream()
                        .filter(subject -> subject.equals(quizResult.getSubject())
                                && quizResult.getDifficulty().equals(quizResultFilter.getDifficulty())))
                .collect(Collectors.toList());
        return filteredQuizResults;
    }

    private List<QuizResult> filterByDifficulty(QuizResultFilter quizResultFilter) {
        List<QuizResult> filteredQuizResults = internalList
                .stream()
                .filter(quizResult -> quizResult.getDifficulty().equals(quizResultFilter.getDifficulty()))
                .collect(Collectors.toList());
        return filteredQuizResults;
    }

    private List<QuizResult> filterByDate(QuizResultFilter quizResultFilter) {
        List<QuizResult> filteredQuizResults = internalList
                .stream()
                .filter(quizResult -> quizResult.isWithinDate(quizResultFilter.getStartDate(),
                        quizResultFilter.getEndDate()))
                .collect(Collectors.toList());
        return filteredQuizResults;
    }

    @Override
    public Iterator<QuizResult> iterator() {
        return internalList.iterator();
    }
}
