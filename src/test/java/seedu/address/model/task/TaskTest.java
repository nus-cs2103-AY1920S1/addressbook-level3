package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;

public class TaskTest {

    @Test
    void getStatusIcon_default_returnN() {
        NoteStub note = new NoteStub(new Title("Lecture 1"), new Content("Hello World"));
        LocalDate date = LocalDate.parse("06/07/2019", Task.FORMAT_USER_INPUT_DATE);
        LocalTime time = LocalTime.parse("1500", Task.FORMAT_USER_INPUT_TIME);
        assertEquals(new TaskForNote(note, date, time).getStatusIcon(),
                "[N]");
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        NoteStub note = new NoteStub(new Title("Lecture 1"), new Content("Hello World"));
        LocalTime time = LocalTime.parse("1500", Task.FORMAT_USER_INPUT_TIME);
        assertThrows(NullPointerException.class, () -> new TaskForNote(note, null, time));
    }

    private class NoteStub extends Note {

        private NoteStub(Title title, Content content) {
            super(title, content);
        }
    }
}
