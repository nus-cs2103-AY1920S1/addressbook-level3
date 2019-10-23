package seedu.address.model.quiz;

import static seedu.address.model.quiz.FilterType.DATE;
import static seedu.address.model.quiz.FilterType.DIFFICULTY;
import static seedu.address.model.quiz.FilterType.SUBJECT;
import static seedu.address.model.quiz.FilterType.SUBJECT_AND_DIFFICULTY;
import static seedu.address.model.quiz.FilterType.SUBJECT_AND_CORRECT_QUESTION;
import static seedu.address.model.quiz.FilterType.SUBJECT_AND_WRONG_QUESTION;

import java.util.Date;
import java.util.List;

import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;

public class QuizResultFilter {

    private List<Subject> subjects;
    private Difficulty difficulty;
    private Date startDate;
    private Date endDate;
    private boolean isCorrectQns;
    private FilterType filterType;

    public QuizResultFilter(List<Subject> subjects) {
        this.subjects = subjects;
        filterType = SUBJECT;
    }

    public QuizResultFilter(Difficulty difficulty) {
        this.difficulty = difficulty;
        filterType = DIFFICULTY;
    }

    public QuizResultFilter(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        filterType = DATE;
    }

    public QuizResultFilter(List<Subject> subjects, boolean isCorrectQns) {
        this.subjects = subjects;
        this.isCorrectQns = isCorrectQns;
        if (isCorrectQns) {
            filterType = SUBJECT_AND_CORRECT_QUESTION;
        } else {
            filterType = SUBJECT_AND_WRONG_QUESTION;
        }

    }

    public QuizResultFilter(List<Subject> subjects, Difficulty difficulty) {
        this.subjects = subjects;
        this.difficulty = difficulty;
        filterType = SUBJECT_AND_DIFFICULTY;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isCorrectQns() {
        return isCorrectQns;
    }

    public FilterType getFilterType() {
        return filterType;
    }
}
