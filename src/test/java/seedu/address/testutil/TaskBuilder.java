package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.note.Note;
import seedu.address.model.question.Question;
import seedu.address.model.task.Heading;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {
    public static final String DEFAULT_HEADING = "A task";
    public static final LocalDate DEFAULT_DATE = LocalDate.parse("31/12/2019", Task.FORMAT_USER_INPUT_DATE);
    public static final LocalTime DEFAULT_TIME = LocalTime.parse("1600", Task.FORMAT_USER_INPUT_TIME);

    private Heading heading;
    private LocalDate date;
    private LocalTime time;
    private boolean isDone;

    public TaskBuilder() {
        heading = new Heading(DEFAULT_HEADING);
        date = DEFAULT_DATE;
        time = DEFAULT_TIME;
        isDone = false;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code task}.
     */
    public TaskBuilder(Task task) {
        heading = task.getHeading();
        date = task.getDate();
        time = task.getTime();
        isDone = task.getStatus();
    }

    /**
     * Sets the {@code Heading} of the {@code Task} that we are building.
     */
    public TaskBuilder withHeading(String heading) {
        this.heading = new Heading(heading);
        return this;
    }

    /**
     * Sets the {@code Heading} of the {@code Task} that we are building with a {@code Note}.
     */
    public TaskBuilder withNote(Note note) {
        this.heading = new Heading(note.getTitle().toString());
        return this;
    }

    /**
     * Sets the {@code Heading} of the {@code Task} that we are building with a {@code Question}.
     */
    public TaskBuilder withQuestion(Question question) {
        this.heading = new Heading(question.getQuestionBody().toString());
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String date) {
        this.date = LocalDate.parse(date, Task.FORMAT_USER_INPUT_DATE);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Task} that we are building.
     */
    public TaskBuilder withTime(String time) {
        this.time = LocalTime.parse(time, Task.FORMAT_USER_INPUT_TIME);
        return this;
    }

    /**
     * Sets the {@code isDone} of the {@code Task} that we are building.
     */
    public TaskBuilder withStatus(boolean isDone) {
        this.isDone = isDone;
        return this;
    }


    public Task build() {
        return new Task(heading, date, time, isDone);
    }
}
