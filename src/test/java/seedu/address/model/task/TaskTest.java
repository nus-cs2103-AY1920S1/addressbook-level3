package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.note.Content;
import seedu.address.model.note.Title;

public class TaskTest {

    @Test
    void getStatusIcon_default_returnN() {
        NoteStub note = new NoteStub(new Title("Lecture 1"), new Content("Hello World"));
        String date = "06/07/2019";
        String time = "1500";
        assertEquals(new TaskForNote(note, date, time).getStatusIcon(),
                "[N]");
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        NoteStub note = new NoteStub(new Title("Lecture 1"), new Content("Hello World"));
        String time = "1500";
        assertThrows(NullPointerException.class, () -> new TaskForNote(note, null, time));
    }
}
