package tagline.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tagline.storage.note.JsonAdaptedNote.MISSING_FIELD_MESSAGE_FORMAT;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.note.TypicalNotes.ULTRON;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.note.Content;
import tagline.model.note.NoteId;
import tagline.model.note.NoteIdCounter;
import tagline.model.note.TimeCreated;
import tagline.model.note.TimeLastEdited;
import tagline.model.note.Title;
import tagline.model.tag.ContactTag;
import tagline.storage.note.JsonAdaptedNote;
import tagline.storage.tag.JsonAdaptedContactTag;
import tagline.storage.tag.JsonAdaptedTag;

public class JsonAdaptedNoteTest {

    private static final String INVALID_NOTEID = "+651234";
    //private static final String INVALID_TITLE = "R@chel";
    //private static final String INVALID_CONTENT = "friends";
    private static final String INVALID_TIMECREATED = "12-Oct-2019 12:12:12";
    //private static final String INVALID_TIMELASTUPDATED = "12-Oct-2019 12:12:12";
    private static final String INVALID_NOTEIDCOUNT = "3a4";

    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NOTEID = ULTRON.getNoteId().toString();
    private static final String VALID_TITLE = ULTRON.getTitle().toString();
    private static final String VALID_CONTENT = ULTRON.getContent().toString();
    private static final String VALID_TIMECREATED = ULTRON.getTimeCreated().getTime().getStorageString();
    private static final String VALID_TIMELASTUPDATED = ULTRON.getTimeLastEdited().getTime().getStorageString();
    private static final String VALID_NOTEIDCOUNT = "78";
    private static final List<JsonAdaptedTag> VALID_TAGS = ULTRON.getTags().stream()
        .flatMap(tag -> {
            if (tag instanceof ContactTag) {
                return Stream.of(new JsonAdaptedContactTag((ContactTag) tag));
            } else {
                return Stream.empty();
            }
        })
        .collect(Collectors.toList());

    @Test
    public void toModelType_validNoteDetails_returnsNote() throws Exception {
        // save the previous count
        long currCount = NoteIdCounter.getCount();
        NoteIdCounter.setZero();
        // set the NoteIdCounter
        NoteIdCounter.setCountFromStorage(VALID_NOTEIDCOUNT);

        JsonAdaptedNote note = new JsonAdaptedNote(ULTRON);
        NoteIdCounter.setZero(); //simulates reset of counter after closing app
        assertFalse(VALID_NOTEIDCOUNT.equals(NoteIdCounter.getCount().toString()));
        assertEquals(ULTRON, note.toModelType());

        //noteIdCounter restores after bringing back a Note
        assertEquals(VALID_NOTEIDCOUNT, NoteIdCounter.getCount().toString());

        // Reset Counter to original value to prevent disruption of other test cases
        NoteIdCounter.setCount(currCount);
    }

    @Test
    public void toModelType_invalidNoteId_throwsIllegalValueException() {

        JsonAdaptedNote note =
            new JsonAdaptedNote(INVALID_NOTEID, VALID_TITLE, VALID_CONTENT, VALID_TIMECREATED,
                VALID_TIMELASTUPDATED, VALID_NOTEIDCOUNT, VALID_TAGS);
        String expectedMessage = NoteId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    }

    @Test
    public void toModelType_nullNoteId_throwsIllegalValueException() {

        JsonAdaptedNote note =
            new JsonAdaptedNote(null, VALID_TITLE, VALID_CONTENT, VALID_TIMECREATED,
                VALID_TIMELASTUPDATED, VALID_NOTEIDCOUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NoteId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    }

    //all strings are legal title
    //@Test
    //public void toModelType_invalidTitle_throwsIllegalValueException() {
    //}

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {

        JsonAdaptedNote note =
            new JsonAdaptedNote(VALID_NOTEID, null, VALID_CONTENT, VALID_TIMECREATED,
                VALID_TIMELASTUPDATED, VALID_NOTEIDCOUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    }

    //all strings are legal content
    //@Test
    //public void toModelType_invalidContent_throwsIllegalValueException() {
    //}

    @Test
    public void toModelType_nullContent_throwsIllegalValueException() {

        JsonAdaptedNote note =
            new JsonAdaptedNote(VALID_NOTEID, VALID_TITLE, null, VALID_TIMECREATED,
                VALID_TIMELASTUPDATED, VALID_NOTEIDCOUNT, VALID_TAGS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    }


    //too hard to do regex for dates
    //@Test
    //public void toModelType_invalidContent_throwsIllegalValueException() {
    //}

    @Test
    public void toModelType_nullTimeCreated_throwsIllegalValueException() {

        JsonAdaptedNote note =
            new JsonAdaptedNote(VALID_NOTEID, VALID_TITLE, VALID_CONTENT, null,
                VALID_TIMELASTUPDATED, VALID_NOTEIDCOUNT, VALID_TAGS);
        NoteIdCounter.setZero(); //simulates reset of counter after closing app
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeCreated.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    }

    //too hard to do regex for dates
    //@Test
    //public void toModelType_invalidTimeLastUpdated_throwsIllegalValueException() {
    //}

    @Test
    public void toModelType_nullTimeLastUpdated_throwsIllegalValueException() {

        JsonAdaptedNote note =
            new JsonAdaptedNote(VALID_NOTEID, VALID_TITLE, VALID_CONTENT, VALID_TIMECREATED,
                null, VALID_NOTEIDCOUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeLastEdited.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    }

    @Test
    public void toModelType_invalidNoteIdCount_throwsIllegalValueException() {

        JsonAdaptedNote note =
            new JsonAdaptedNote(VALID_NOTEID, VALID_TITLE, VALID_CONTENT, INVALID_TIMECREATED,
                VALID_TIMELASTUPDATED, INVALID_NOTEIDCOUNT, VALID_TAGS);
        String expectedMessage = NoteIdCounter.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    }

    @Test
    public void toModelType_nullNoteIdCount_throwsIllegalValueException() {

        JsonAdaptedNote note =
            new JsonAdaptedNote(VALID_NOTEID, VALID_TITLE, VALID_CONTENT, VALID_TIMECREATED,
                VALID_TIMELASTUPDATED, null, VALID_TAGS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NoteIdCounter.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        /*
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG, 1));
        JsonAdaptedNote note =
                new JsonAdaptedNote(VALID_NOTEID, VALID_TITLE, VALID_CONTENT, VALID_TIMECREATED,
                        VALID_TIMELASTUPDATED, VALID_NOTEIDCOUNT, invalidTags);
        assertThrows(IllegalValueException.class, note::toModelType);
        */
    }

    ////testing the piggyback of NoteIDCreator
    //@Test
    //public void toModelType_nullNoteId_throwsIllegalValueException() {
    //    // save the previous count
    //    long currCount = NoteIdCounter.getCount();
    //    NoteIdCounter.setZero();
    //    // set the NoteIdCounter
    //    NoteIdCounter.setCountFromStorage(VALID_NOTEIDCOUNT);

    //    JsonAdaptedNote note =
    //            new JsonAdaptedNote(null, VALID_TITLE, VALID_CONTENT, VALID_TIMECREATED,
    //                    VALID_TIMELASTUPDATED, VALID_NOTEIDCOUNT, VALID_TAGS);
    //    NoteIdCounter.setZero(); //simulates reset of counter after closing app
    //    String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NoteId.class.getSimpleName());
    //    assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);

    //    assertEquals(VALID_NOTEIDCOUNT, NoteIdCounter.getCount().toString());

    //    // Reset Counter to original value to prevent disruption of other test cases
    //    NoteIdCounter.setCount(currCount);
    //}
}
