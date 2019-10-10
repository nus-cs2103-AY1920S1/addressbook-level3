package seedu.address.model.task;

/**
 * Represents a revision task for doing questions.
 */
public class TaskForQuestion extends Task {
    private QuestionStub question;

    /**
     * Constructs a new revision task for doing questions.
     *
     * @param question The question to be revised.
     * @param date The date by when the task should be done.
     * @param time The time in a day by which the task should be done.
     */
    public TaskForQuestion(QuestionStub question, String date, String time) {
        super(date, time);
        this.question = question;
    }

    @Override
    public String toString() {
        return super.getStatusIcon() + " " + question.toString() + " by: "
                + super.getDate().format(FORMAT_FILE_DATE_STRING)
                + " " + super.getTime().format(FORMAT_FILE_TIME_STRING);
    }
}
