package seedu.revision.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalMcqs.H_ANSWERABLE;
import static seedu.revision.testutil.TypicalMcqs.I_ANSWERABLE;
import static seedu.revision.testutil.TypicalMcqs.MCQ_STUB;
import static seedu.revision.testutil.TypicalMcqs.getTypicalRevisionTool;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.RevisionTool;

public class JsonRevisionToolStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonRevisionToolStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyRevisionTool> readAddressBook(String filePath) throws Exception {
        return new JsonRevisionToolStorage(Paths.get(filePath)).readRevisionTool(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatTestBank.json"));
    }

    @Test
    public void readAddressBook_invalidAnswerableAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAnswerableTestBank.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidAnswerableAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidAnswerableTestBank.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        RevisionTool original = getTypicalRevisionTool();
        JsonRevisionToolStorage jsonAddressBookStorage = new JsonRevisionToolStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveRevisionTool(original, filePath);
        ReadOnlyRevisionTool readBack = jsonAddressBookStorage.readRevisionTool(filePath).get();
        assertEquals(original, new RevisionTool(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addAnswerable(H_ANSWERABLE);
        original.removeAnswerable(MCQ_STUB);
        jsonAddressBookStorage.saveRevisionTool(original, filePath);
        readBack = jsonAddressBookStorage.readRevisionTool(filePath).get();
        assertEquals(original, new RevisionTool(readBack));

        // Save and read without specifying file path
        original.addAnswerable(I_ANSWERABLE);
        jsonAddressBookStorage.saveRevisionTool(original); // file path not specified
        readBack = jsonAddressBookStorage.readRevisionTool().get(); // file path not specified
        assertEquals(original, new RevisionTool(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyRevisionTool addressBook, String filePath) {
        try {
            new JsonRevisionToolStorage(Paths.get(filePath))
                    .saveRevisionTool(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new RevisionTool(), null));
    }
}
