package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNotes.SECRETDOC;
import static seedu.address.testutil.TypicalNotes.getTypicalNoteBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AddressBook;
import seedu.address.model.NoteBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyNoteBook;

public class JsonNoteBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonNoteBookStorageTest");
    private static final String PASSWORD = "password1";

    @TempDir
    public Path testFolder;

    @Test
    public void readNoteBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readNoteBook(null));
    }

    private java.util.Optional<ReadOnlyNoteBook> readNoteBook(String filePath) throws Exception {
        return new JsonNoteBookStorage(Paths.get(filePath)).readNoteBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readNoteBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readNoteBook("notJsonFormatNoteBook.json"));
    }

    @Test
    public void readNoteBook_invalidNoteBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNoteBook("invalidNoteBook.json"));
    }

    @Test
    public void readNoteBook_invalidAndValidNoteBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNoteBook("invalidAndValidNoteBook.json"));
    }

    @Test
    public void readAndSaveNoteBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempNoteBook.json");
        NoteBook original = getTypicalNoteBook();
        JsonNoteBookStorage jsonNoteBookStorage = new JsonNoteBookStorage(filePath, PASSWORD);

        // Save in new file and read back
        jsonNoteBookStorage.saveNoteBook(original, filePath);
        ReadOnlyNoteBook readBack = jsonNoteBookStorage.readNoteBook(filePath).get();
        assertEquals(original, new NoteBook(readBack));

        // Modify data, overwrite exiting file, and read back
        //        original.addNote(SECRETDIARY);
        original.removeNote(SECRETDOC);
        jsonNoteBookStorage.saveNoteBook(original, filePath);
        readBack = jsonNoteBookStorage.readNoteBook(filePath).get();
        assertEquals(original, new NoteBook(readBack));

        // Save and read without specifying file path
        //        original.addNote(SECRETRECORDS);
        jsonNoteBookStorage.saveNoteBook(original); // file path not specified
        readBack = jsonNoteBookStorage.readNoteBook().get(); // file path not specified
        assertEquals(original, new NoteBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath), PASSWORD)
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }
}
