package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static tagline.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static tagline.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static tagline.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static tagline.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static tagline.logic.commands.NoteCommandTestUtil.*;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_INCIDENT;
//import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_NOTEID_INCIDENT;
//import static tagline.logic.commands.NoteCommandTestUtil.VALID_NOTEID_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMECREATED_INCIDENT;
//import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMECREATED_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMELASTUPDATED_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_INCIDENT;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalNotes.INCIDENT;
import static tagline.testutil.TypicalNotes.PROTECTOR;
//import static tagline.testutil.TypicalPersons.ALICE;
//import static tagline.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import tagline.testutil.NoteBuilder;

public class NoteTest {

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

        //// different phone and email -> returns false
        //Note editedProtector = new NoteBuilder(PROTECTOR).withContent(VALID_CONTENT_INCIDENT)
        //    .withTimeCreated(VALID_TIMECREATED_INCIDENT).build();
        //assertFalse(PROTECTOR.isSameNote(editedProtector));

        // different id -> returns false
        Note editedProtector = new NoteBuilder(PROTECTOR).withNoteId(VALID_NOTEID_INCIDENT).build();
        //.withTimeCreated(VALID_TIMECREATED_PROTECTOR).build();
        assertFalse(PROTECTOR.isSameNote(editedProtector));

        //// different content -> returns false
        //editedProtector = new NoteBuilder(PROTECTOR).withContent(VALID_CONTENT_INCIDENT)
        //    .withTimeCreated(VALID_TIMECREATED_PROTECTOR).build();
        //assertFalse(PROTECTOR.isSameNote(editedProtector));

        //// different timecreated -> returns false
        //editedProtector = new NoteBuilder(PROTECTOR).withContent(VALID_CONTENT_PROTECTOR)
        //        .withTimeCreated(VALID_TIMECREATED_INCIDENT).build();
        //assertFalse(PROTECTOR.isSameNote(editedProtector));

        //// different content and timecreated -> returns false
        //editedProtector = new NoteBuilder(PROTECTOR).withContent(VALID_CONTENT_INCIDENT)
        //        .withTimeCreated(VALID_TIMECREATED_INCIDENT).build();
        //assertFalse(PROTECTOR.isSameNote(editedProtector));

        //// same name, same phone, different attributes -> returns true
        editedProtector = new NoteBuilder(PROTECTOR).withTimeLastUpdated(VALID_TIMELASTUPDATED_INCIDENT)
                //.withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(PROTECTOR.isSameNote(editedProtector));

        //// same name, same email, different attributes -> returns true
        //editedProtector = new NoteBuilder(PROTECTOR).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
        //        .withTags(VALID_TAG_HUSBAND).build();
        //assertTrue(PROTECTOR.isSameNote(editedProtector));

        // same name, same phone, same email, different attributes -> returns true
        //editedProtector = new NoteBuilder(PROTECTOR).withAddress(VALID_ADDRESS_BOB)
        //           .withTags(VALID_TAG_HUSBAND).build();
        //assertTrue(PROTECTOR.isSameNote(editedProtector));
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
        editedProtector = new NoteBuilder(PROTECTOR).withTimeLastUpdated(VALID_TIMELASTUPDATED_INCIDENT).build();
        assertFalse(PROTECTOR.equals(editedProtector));

        // different tags -> returns false
        editedProtector = new NoteBuilder(PROTECTOR).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(PROTECTOR.equals(editedProtector));
    }
}
