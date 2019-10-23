package seedu.address.model.task;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.question.Question;

/**
 * Represents a task for revision of questions.
 */
public class TaskForQuestion extends Task {
    private static final String TYPE = "QUESTION";

    /**
     * Constructs a new revision task for questions. Date field must be non-null.
     *
     * @param question The question to be revised.
     * @param date The date by when the task should be done.
     * @param time The time in a day by which the task should be done.
     */
    public TaskForQuestion(Question question, LocalDate date, LocalTime time) {
        super(date, time);
        super.heading = new Heading(question.getQuestionBody().toString());
    }

    @Override
    public String toString() {
        return super.getStatusIcon() + " " + TYPE + " : " + super.heading.toString() + " by: "
                + super.getDate().format(FORMAT_FILE_DATE_STRING) + " "
                + super.getTime().format(FORMAT_FILE_TIME_STRING);
    }
}
