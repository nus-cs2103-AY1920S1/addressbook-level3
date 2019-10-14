package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.note.Note;

/**
 * Represents a task for revision of notes.
 */
public class TaskForNote extends Task {
    private Note note;

    /**
     * Constructs a new revision task for lecture notes. Date field must be non-null.
     *
     * @param note The lecture notes to be revised.
     * @param date The date by when the task should be done.
     * @param time The time in a day by which the task should be done.
     */
    public TaskForNote(Note note, LocalDate date, LocalTime time) {
        super(date, time);
        this.note = note;
    }

    public void setNote(Note note) {
        requireNonNull(note);
        this.note = note;
    }

    public Note getNote() {
        return this.note;
    }

    @Override
    public String toString() {
        return super.getStatusIcon() + " " + note.toString() + " by: " + super.getDate().format(FORMAT_FILE_DATE_STRING)
                + " " + super.getTime().format(FORMAT_FILE_TIME_STRING);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskForNote)) {
            return false;
        }

        TaskForNote otherTask = (TaskForNote) other;
        return this.note.isSameNote(otherTask.note)
                && super.getDate().equals(otherTask.getDate())
                && super.getTime().equals(otherTask.getTime())
                && super.getStatusIcon().equals(otherTask.getStatusIcon());
    }
}
