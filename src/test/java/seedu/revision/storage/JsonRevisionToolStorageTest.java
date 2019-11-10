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
    public void readRevisionTool_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readRevisionTool(null));
    }

    private java.util.Optional<ReadOnlyRevisionTool> readRevisionTool(String filePath) throws Exception {
        return new JsonRevisionToolStorage(Paths.get(filePath)).readRevisionTool(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRevisionTool("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readRevisionTool("notJsonFormatTestBank.json"));
    }

    @Test
    public void readRevisionTool_invalidAnswerableRevisionTool_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readRevisionTool("invalidAnswerableTestBank.json"));
    }

    @Test
    public void readRevisionTool_invalidAndValidAnswerableRevisionTool_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readRevisionTool("invalidAndValidAnswerableTestBank.json"));
    }

    @Test
    public void readAndSaveRevisionTool_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        RevisionTool original = getTypicalRevisionTool();
        JsonRevisionToolStorage jsonRevisionToolStorage = new JsonRevisionToolStorage(filePath);

        // Save in new file and read back
        jsonRevisionToolStorage.saveRevisionTool(original, filePath);
        ReadOnlyRevisionTool readBack = jsonRevisionToolStorage.readRevisionTool(filePath).get();
        assertEquals(original, new RevisionTool(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addAnswerable(H_ANSWERABLE);
        original.removeAnswerable(MCQ_STUB);
        jsonRevisionToolStorage.saveRevisionTool(original, filePath);
        readBack = jsonRevisionToolStorage.readRevisionTool(filePath).get();
        assertEquals(original, new RevisionTool(readBack));

        // Save and read without specifying file path
        original.addAnswerable(I_ANSWERABLE);
        jsonRevisionToolStorage.saveRevisionTool(original); // file path not specified
        readBack = jsonRevisionToolStorage.readRevisionTool().get(); // file path not specified
        assertEquals(original, new RevisionTool(readBack));

    }

    @Test
    public void saveRevisionTool_nullRevisionTool_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveRevisionTool(null, "SomeFile.json"));
    }

    /**
     * Saves {@code revisionTool} at the specified {@code filePath}.
     */
    private void saveRevisionTool(ReadOnlyRevisionTool revisionTool, String filePath) {
        try {
            new JsonRevisionToolStorage(Paths.get(filePath))
                    .saveRevisionTool(revisionTool, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRevisionTool_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveRevisionTool(new RevisionTool(), null));
    }
}
