package tagline.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import tagline.commons.exceptions.IllegalValueException;
import tagline.commons.util.JsonUtil;
import tagline.model.note.NoteBook;
import tagline.model.note.NoteIdCounter;
import tagline.storage.note.JsonSerializableNoteBook;
import tagline.testutil.note.TypicalNotes;

public class JsonSerializableNoteBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableNoteBookTest");
    private static final Path TYPICAL_NOTES_FILE = TEST_DATA_FOLDER.resolve("typicalNotesNoteBook.json");
    private static final Path INVALID_NOTE_FILE = TEST_DATA_FOLDER.resolve("invalidNoteNoteBook.json");
    private static final Path DUPLICATE_NOTE_FILE = TEST_DATA_FOLDER.resolve("duplicateNoteNoteBook.json");

    @Test
    public void toModelType_typicalNotesFile_success() throws Exception {
        JsonSerializableNoteBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_NOTES_FILE,
                JsonSerializableNoteBook.class).get();

        NoteBook addressBookFromFile = dataFromFile.toModelType();
        NoteBook typicalNotesNoteBook = TypicalNotes.getTypicalNoteBook();
        assertEquals(addressBookFromFile, typicalNotesNoteBook);
    }

    // impure test to check if NoteIDCounter can be retrieved from JSON
    @Test
    public void toModelTypeNoteIdCounter_typicalNotesFile_success() throws Exception {
        final String validNoteIdCount = "329"; //its the one in the testcase file
        // save the previous count
        long currCount = NoteIdCounter.getCount();
        NoteIdCounter.setZero();
        // set the NoteIdCounter
        //NoteIdCounter.setCountFromStorage(validNoteIdCount);
        //assertEquals(validNoteIdCount, NoteIdCounter.getCount().toString());

        JsonSerializableNoteBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_NOTES_FILE,
                JsonSerializableNoteBook.class).get();
        NoteIdCounter.setZero(); //simulate memory wipe

        NoteBook addressBookFromFile = dataFromFile.toModelType();
        NoteBook typicalNotesNoteBook = TypicalNotes.getTypicalNoteBook();
        assertEquals(addressBookFromFile, typicalNotesNoteBook);

        // check if NoteIdCounter can be restored from Saved Json File
        assertEquals(validNoteIdCount, NoteIdCounter.getCount().toString());
        // Reset Counter to original value to prevent disruption of other test cases
        NoteIdCounter.setCount(currCount);
    }

    @Test
    public void toModelType_invalidNoteFile_throwsIllegalValueException() throws Exception {
        JsonSerializableNoteBook dataFromFile = JsonUtil.readJsonFile(INVALID_NOTE_FILE,
                JsonSerializableNoteBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateNotes_throwsIllegalValueException() throws Exception {
        JsonSerializableNoteBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_NOTE_FILE,
                JsonSerializableNoteBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableNoteBook.MESSAGE_DUPLICATE_NOTE,
                dataFromFile::toModelType);
    }

}
