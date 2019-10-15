package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNotes.PIPELINE;
import static seedu.address.testutil.TypicalNotes.SAMPLE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.NoteBuilder;

public class NoteTest {
    @Test
    public void toString_format_success() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("sampleTag"));
        Note note = new Note(new Title("this is a title"), new Content("this is a content"), tags);
        assertEquals(note.toString(), "\nTitle: this is a title\nContent: this is a content\nTags: [sampleTag]");
    }

    @Test
    public void requireNonNull_noTitleProvided_throwsIllegalArgumentException() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> new Note(new Title("title"),
                new Content(""), null));
    }
    
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Note note = new NoteBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> note.getTags().remove(0));
    }

    @Test
    public void isSameNote() {
        // same object -> returns true
        assertTrue(SAMPLE.isSameNote(SAMPLE));

        // null -> returns false
        assertFalse(SAMPLE.isSameNote(null));

        // different title -> returns false
        Note differentTitle = new NoteBuilder(SAMPLE).withTitle("Different Sample Title").build();
        assertFalse(SAMPLE.isSameNote(differentTitle));

        // same title -> returns true
        Note sameTitle = new NoteBuilder(SAMPLE).withTitle("Sample Title").build();
        assertTrue(SAMPLE.isSameNote(sameTitle));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Note sampleCopy = new NoteBuilder(SAMPLE).build();
        assertTrue(SAMPLE.equals(sampleCopy));

        // same object -> returns true
        assertTrue(SAMPLE.equals(SAMPLE));

        // null -> returns false
        assertFalse(SAMPLE.equals(null));

        // different type -> returns false
        assertFalse(SAMPLE.equals(5));

        // different note -> returns false
        assertFalse(SAMPLE.equals(PIPELINE));

        // different name -> returns false
        Note editedAlice = new NoteBuilder(SAMPLE).withTitle("Different Sample Title").build();
        assertFalse(SAMPLE.equals(editedAlice));
    }
}
