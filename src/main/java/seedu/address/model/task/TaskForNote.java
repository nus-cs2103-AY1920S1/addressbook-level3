package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.note.Note;

/**
 * Represents a task for revision of notes.
 */
public class TaskForNote extends Task {
    private static final String TYPE = "NOTE";

    /**
     * Constructs a new revision task for lecture notes. Date field must be non-null.
     *
     * @param note The lecture notes to be revised.
     * @param date The date by when the task should be done.
     * @param time The time in a day by which the task should be done.
     */
    public TaskForNote(Note note, LocalDate date, LocalTime time) {
        super(date, time);
        requireNonNull(note);
        super.heading = new Heading(note.getTitle().toString());
    }

    @Override
    public String toString() {
        return super.getStatusIcon() + " " + TYPE + " : " + super.heading.toString() + " by: "
                + super.getDate().format(FORMAT_FILE_DATE_STRING) + " "
                + super.getTime().format(FORMAT_FILE_TIME_STRING);
    }
}
