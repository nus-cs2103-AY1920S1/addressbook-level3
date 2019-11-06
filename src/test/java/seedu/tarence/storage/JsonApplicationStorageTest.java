package seedu.tarence.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.model.Application;
import seedu.tarence.model.ReadOnlyApplication;

//import static seedu.tarence.testutil.TypicalPersons.ALICE;
//import static seedu.tarence.testutil.TypicalPersons.HOON;
//import static seedu.tarence.testutil.TypicalPersons.IDA;
//import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

public class JsonApplicationStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonApplicationStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readApplication_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readApplicationBook(null));
    }

    private java.util.Optional<ReadOnlyApplication> readApplicationBook(String filePath) throws Exception {
        return new JsonApplicationStorage(Paths.get(filePath)).readApplication(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readApplicationBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readApplicationBook("notJsonFormatApplication.json"));
    }

    @Test
    public void readApplication_invalidPersonApplication_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readApplicationBook("invalidPersonApplication.json"));
    }

    @Test
    public void readApplication_invalidAndValidPersonApplication_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readApplicationBook("invalidAndValidPersonApplication.json"));
    }

    /*
    TODO: Add modules into typical application
    @Test
    public void readAndSaveApplication_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempApplication.json");
        Application original = getTypicalApplication();
        JsonApplicationStorage jsonApplicationStorage = new JsonApplicationStorage(filePath);

        // Save in new file and read back
        jsonApplicationStorage.saveApplication(original, filePath);
        ReadOnlyApplication readBack = jsonApplicationStorage.readApplication(filePath).get();
        assertEquals(original, new Application(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonApplicationStorage.saveApplication(original, filePath);
        readBack = jsonApplicationStorage.readApplication(filePath).get();
        assertEquals(original, new Application(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonApplicationStorage.saveApplication(original); // file path not specified
        readBack = jsonApplicationStorage.readApplication().get(); // file path not specified
        assertEquals(original, new Application(readBack));

    }

     */

    @Test
    public void saveApplication_nullApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveApplication(null, "SomeFile.json"));
    }

    /**
     * Saves {@code Application} at the specified {@code filePath}.
     */
    private void saveApplication(ReadOnlyApplication application, String filePath) {
        try {
            new JsonApplicationStorage(Paths.get(filePath))
                    .saveApplication(application, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveApplication_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveApplication(new Application(), null));
    }
}
