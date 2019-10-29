package seedu.address.model.quiz;

import static seedu.address.model.quiz.FilterType.CORRECT;
import static seedu.address.model.quiz.FilterType.DATE;
import static seedu.address.model.quiz.FilterType.DIFFICULTY;
import static seedu.address.model.quiz.FilterType.INCORRECT;
import static seedu.address.model.quiz.FilterType.NONE;
import static seedu.address.model.quiz.FilterType.SUBJECT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;

/**
 * A class to store the operations to filter quiz results by.
 */
public class QuizResultFilter {

    private List<Subject> subjects;
    private Difficulty difficulty;
    private Date startDate;
    private Date endDate;
    private Boolean isCorrectQns;
    private Stack<FilterType> operations = new Stack<>();

    public QuizResultFilter() {}

    public QuizResultFilter(List<Subject> subjects) {
        this.subjects = subjects;
        if (subjects.isEmpty()) {
            operations.push(NONE);
        } else {
            operations.push(SUBJECT);
        }
    }

    public QuizResultFilter(Difficulty difficulty) {
        this.difficulty = difficulty;
        operations.push(DIFFICULTY);
    }

    public QuizResultFilter(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        operations.push(DATE);
    }

    public QuizResultFilter(List<Subject> subjects, boolean isCorrectQns) {
        this.subjects = subjects;
        this.isCorrectQns = isCorrectQns;
        if (isCorrectQns) {
            operations.push(CORRECT);
        } else {
            operations.push(INCORRECT);
        }

        if (subjects.isEmpty()) {
            operations.push(NONE);
        } else {
            operations.push(SUBJECT);
        }
    }

    public QuizResultFilter(List<Subject> subjects, Difficulty difficulty) {
        this.subjects = subjects;
        this.difficulty = difficulty;
        if (!subjects.isEmpty()) {
            operations.push(SUBJECT);
        }
        operations.push(DIFFICULTY);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean getIsCorrectQns() {
        return isCorrectQns;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Stack<FilterType> getOperations() {
        return operations;
    }

    public void setOperation(Subject s, Difficulty d) {
        this.subjects = new ArrayList<>();
        this.subjects.add(s);
        this.difficulty = d;
        if (operations.empty() && startDate != null && endDate != null) {
            operations.push(DATE);
        }
        operations.push(SUBJECT);
        operations.push(DIFFICULTY);
    }

    public void setOperation(Difficulty d) {
        this.difficulty = d;
        if (operations.empty() && isCorrectQns != null) {
            if (isCorrectQns) {
                operations.push(CORRECT);
            } else {
                operations.push(INCORRECT);
            }
        }
        if (!subjects.isEmpty()) {
            operations.push(SUBJECT);
        }
        operations.push(DIFFICULTY);
    }
}
