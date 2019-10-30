package seedu.address.testutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.FilterType;
import seedu.address.model.quiz.QuizResultFilter;

/**
 * A utility class for helping with building QuizResultFilter objects.
 */
public class QuizResultFilterBuilder {

    private QuizResultFilter quizResultFilter;
    private List<Subject> subjects;
    private Difficulty difficulty;
    private Date startDate;
    private Date endDate;
    private Boolean isCorrectQns;
    private Stack<FilterType> operations;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public QuizResultFilterBuilder() {
        quizResultFilter = new QuizResultFilter();
    }

    /**
     * Sets the {@code Subjects} of the {@code QuizResultFilter} that we are building.
     */
    public QuizResultFilterBuilder withSubjects(List<String> subjects) {
        this.subjects = new ArrayList<>();
        for (String subject : subjects) {
            this.subjects.add(new Subject(subject));
        }
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code QuizResultFilter} that we are building.
     */
    public QuizResultFilterBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code QuizResultFilter} that we are building.
     */
    public QuizResultFilterBuilder withStartDate(String startDate) throws ParseException {
        this.startDate = dateFormatter.parse(startDate);
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code QuizResultFilter} that we are building.
     */
    public QuizResultFilterBuilder withEndDate(String endDate) throws ParseException {
        this.endDate = dateFormatter.parse(endDate);
        return this;
    }

    /**
     * Sets the {@code isCorrectQns} of the {@code QuizResultFilter} that we are building.
     */
    public QuizResultFilterBuilder withIsCorrectQns(String isCorrectQns) {
        this.isCorrectQns = Boolean.valueOf(isCorrectQns);
        return this;
    }

    /**
     * Sets the {@code Operations} of the {@code QuizResultFilter} that we are building.
     */
    public QuizResultFilterBuilder withOperations(List<FilterType> operations) {
        this.operations = new Stack<>();
        for (FilterType operation : operations) {
            this.operations.push(operation);
        }
        return this;
    }

    public QuizResultFilter buildWithSubjects() {
        return new QuizResultFilter(subjects);
    }

    public QuizResultFilter buildWithDates() {
        return new QuizResultFilter(startDate, endDate);
    }

    public QuizResultFilter buildWithSubjectsAndResult() {
        return new QuizResultFilter(subjects, isCorrectQns);
    }

    public QuizResultFilter buildWithSubjectsAndDifficulty() {
        return new QuizResultFilter(subjects, difficulty);
    }

    public QuizResultFilter buildEmptySubjectList() {
        return new QuizResultFilter(new ArrayList<>());
    }

    public QuizResultFilter build() {
        return new QuizResultFilter();
    }
}
