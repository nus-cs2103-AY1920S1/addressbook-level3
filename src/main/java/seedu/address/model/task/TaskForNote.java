package seedu.address.model.task;

/**
 * Represents a task for revision of notes.
 */
public class TaskForNote extends Task {
    private NoteStub note;

    /**
     * Constructs a new revision task for lecture notes. Date field must be non-null.
     *
     * @param note The lecture notes to be revised.
     * @param date The date by when the task should be done.
     * @param time The time in a day by which the task should be done.
     */
    public TaskForNote(NoteStub note, String date, String time) {
        super(date, time);
        this.note = note;
    }

    @Override
    public String toString() {
        return super.getStatusIcon() + " " + note.toString() + " by: " + super.getDate().format(FORMAT_FILE_DATE_STRING)
                + " " + super.getTime().format(FORMAT_FILE_TIME_STRING);
    }
}
