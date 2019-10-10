package seedu.address.model.task;

public class TaskForNote extends Task {
    private NoteStub note;

    public TaskForNote(NoteStub note, String date, String time) {
        super(date, time);
        this.note = note;
    }

    @Override
    public String toString() {
        return super.getStatusIcon() + " " + note.toString() + " by: " + super.date.format(FORMAT_FILE_DATE_STRING)
                + " " + super.time.format(FORMAT_FILE_TIME_STRING);
    }
}
