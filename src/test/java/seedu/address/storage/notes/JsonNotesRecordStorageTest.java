package seedu.address.storage.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.note.TypicalNotes.NOTE1;
import static seedu.address.testutil.note.TypicalNotes.NOT_IN_TYPICAL;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.storage.note.JsonNotesRecordStorage;
import seedu.address.testutil.note.NoteBuilder;
import seedu.address.testutil.note.TypicalNotes;

public class JsonNotesRecordStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "JsonNotesRecordStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readNoteRecord_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readNotesRecord(null));
    }

    private java.util.Optional<ReadOnlyNotesRecord> readNotesRecord(String filePath) throws Exception {
        return new JsonNotesRecordStorage(Paths.get(filePath)).readNotesRecord(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readNotesRecord("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readNotesRecord("notJsonFormatNotesRecord.json"));
    }

    @Test
    public void readNotesRecord_invalidNoteNotesRecord_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNotesRecord("invalidNoteNotesRecord.json"));
    }

    @Test
    public void readNotesRecord_invalidAndValidNotesNotesRecord_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNotesRecord("invalidAndValidNotesNotesRecord.json"));
    }

    @Test
    public void readAndSaveNotesRecord_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempNotesRecord.json");
        NotesRecord original = TypicalNotes.getTypicalNotesRecord();
        JsonNotesRecordStorage jsonNotesRecordStorage = new JsonNotesRecordStorage(filePath);

        // Save in new file and read back
        jsonNotesRecordStorage.saveNotesRecord(original, filePath);
        ReadOnlyNotesRecord readBack = jsonNotesRecordStorage.readNotesRecord(filePath).get();
        assertEquals(original, new NotesRecord(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addNote(NOT_IN_TYPICAL);
        original.removeNote(NOTE1);
        jsonNotesRecordStorage.saveNotesRecord(original, filePath);
        readBack = jsonNotesRecordStorage.readNotesRecord(filePath).get();
        assertEquals(original, new NotesRecord(readBack));

        // Save and read without specifying file path
        original.addNote(new NoteBuilder(NOTE1).withDesc("different note title").build());
        jsonNotesRecordStorage.saveNotesRecord(original); // file path not specified
        readBack = jsonNotesRecordStorage.readNotesRecord().get(); // file path not specified
        assertEquals(original, new NotesRecord(readBack));

    }

    @Test
    public void saveNotesRecord_nullNotesRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveNotesRecord(null, "SomeFile.json"));
    }

    /**
     * Saves {@code notesRecord} at the specified {@code filePath}.
     */
    private void saveNotesRecord(ReadOnlyNotesRecord notesRecord, String filePath) {
        try {
            new JsonNotesRecordStorage(Paths.get(filePath))
                    .saveNotesRecord(notesRecord, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveNotesRecord_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveNotesRecord(new NotesRecord(), null));
    }
}
