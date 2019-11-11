package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_NOTEID_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMECREATED_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMELASTUPDATED_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_INCIDENT;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.note.TypicalNotes.INCIDENT;
import static tagline.testutil.note.TypicalNotes.PROTECTOR;

import org.junit.jupiter.api.Test;

import tagline.testutil.note.NoteBuilder;

public class NoteTest {

    @Test
    public void constructor_invalidNote_throwIllegalArgumentException() {
        // Title and content are both empty
        assertThrows(IllegalArgumentException.class,
                Note.MESSAGE_CONSTRAINTS, () -> new NoteBuilder().withTitle("").withContent("").build());
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Note note = new NoteBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> note.getTags().remove(0));
    }

    @Test
    public void isSameNote() {
        // same object -> returns true
        assertTrue(PROTECTOR.isSameNote(PROTECTOR));

        // null -> returns false
        assertFalse(PROTECTOR.isSameNote(null));

        // different id -> returns false
        Note editedProtector = new NoteBuilder(PROTECTOR).withNoteId(VALID_NOTEID_INCIDENT).build();
        assertFalse(PROTECTOR.isSameNote(editedProtector));

        // different timecreated -> returns true
        editedProtector = new NoteBuilder(PROTECTOR).withTimeCreated(VALID_TIMECREATED_INCIDENT).build();
        assertTrue(PROTECTOR.isSameNote(editedProtector));

        // different timeLastEdited -> returns true
        editedProtector = new NoteBuilder(PROTECTOR).withTimeLastEdited(VALID_TIMELASTUPDATED_INCIDENT).build();
        assertTrue(PROTECTOR.isSameNote(editedProtector));
    }

    @Test
    public void isUniqueNote() {
        // same object -> returns true
        assertTrue(PROTECTOR.isUniqueNote(PROTECTOR));

        // null -> returns false
        assertFalse(PROTECTOR.isUniqueNote(null));

        // different id -> returns false
        Note editedProtector = new NoteBuilder(PROTECTOR).withNoteId(VALID_NOTEID_INCIDENT).build();
        assertFalse(PROTECTOR.isUniqueNote(editedProtector));

        // different title -> returns false
        editedProtector = new NoteBuilder(PROTECTOR).withTitle(VALID_TITLE_INCIDENT).build();
        assertFalse(PROTECTOR.isUniqueNote(editedProtector));

        // different content -> returns false
        editedProtector = new NoteBuilder(PROTECTOR).withContent(VALID_CONTENT_INCIDENT).build();
        assertFalse(PROTECTOR.isUniqueNote(editedProtector));

        // different tag -> returns false
        //editedProtector = new NoteBuilder(PROTECTOR).withTags(VALID_TAG_AVENGERS).build();
        //assertFalse(PROTECTOR.isUniqueNote(editedProtector));

        // different timecreated -> returns true
        editedProtector = new NoteBuilder(PROTECTOR).withTimeCreated(VALID_TIMECREATED_INCIDENT).build();
        assertTrue(PROTECTOR.isUniqueNote(editedProtector));

        // different timeLastEdited -> returns true
        editedProtector = new NoteBuilder(PROTECTOR).withTimeLastEdited(VALID_TIMELASTUPDATED_INCIDENT).build();
        assertTrue(PROTECTOR.isUniqueNote(editedProtector));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Note aliceCopy = new NoteBuilder(PROTECTOR).build();
        assertTrue(PROTECTOR.equals(aliceCopy));

        // same object -> returns true
        assertTrue(PROTECTOR.equals(PROTECTOR));

        // null -> returns false
        assertFalse(PROTECTOR.equals(null));

        // different type -> returns false
        assertFalse(PROTECTOR.equals(5));

        // different note -> returns false
        assertFalse(PROTECTOR.equals(INCIDENT));

        // different noteid -> returns false
        Note editedProtector = new NoteBuilder(PROTECTOR).withNoteId(VALID_NOTEID_INCIDENT).build();
        assertFalse(PROTECTOR.equals(editedProtector));

        // different content -> returns false
        editedProtector = new NoteBuilder(PROTECTOR).withContent(VALID_CONTENT_INCIDENT).build();
        assertFalse(PROTECTOR.equals(editedProtector));

        // different phone -> returns false
        editedProtector = new NoteBuilder(PROTECTOR).withTitle(VALID_TITLE_INCIDENT).build();
        assertFalse(PROTECTOR.equals(editedProtector));

        // different email -> returns false
        editedProtector = new NoteBuilder(PROTECTOR).withTimeCreated(VALID_TIMECREATED_INCIDENT).build();
        assertFalse(PROTECTOR.equals(editedProtector));

        // different address -> returns false
        editedProtector = new NoteBuilder(PROTECTOR).withTimeLastEdited(VALID_TIMELASTUPDATED_INCIDENT).build();
        assertFalse(PROTECTOR.equals(editedProtector));

        // different tags -> returns false
        // editedProtector = new NoteBuilder(PROTECTOR).withTags(VALID_TAG_HUSBAND).build();
        // assertFalse(PROTECTOR.equals(editedProtector));
    }
}
