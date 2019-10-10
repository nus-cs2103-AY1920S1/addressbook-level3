package seedu.address.model.task;

public class TaskForQuestion extends Task {
    private QuestionStub question;

    public TaskForQuestion(QuestionStub question, String date, String time) {
        super(date, time);
        this.question = question;
    }

    @Override
    public String toString() {
        return super.getStatusIcon() + " " + question.toString() + " by: " + super.date.format(FORMAT_FILE_DATE_STRING)
                + " " + super.time.format(FORMAT_FILE_TIME_STRING);
    }
}
