package cs.f10.t1.nursetraverse.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cs.f10.t1.nursetraverse.commons.exceptions.DataConversionException;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.testutil.Assert;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

public class JsonPatientBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonPatientBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPatientBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readPatientBook(null));
    }

    private java.util.Optional<ReadOnlyPatientBook> readPatientBook(String filePath) throws Exception {
        return new JsonPatientBookStorage(Paths.get(filePath)).readPatientBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPatientBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(
                DataConversionException.class, () -> readPatientBook("notJsonFormatPatientBook.json"));
    }

    @Test
    public void readPatientBook_invalidPatientPatientBook_throwDataConversionException() {
        Assert.assertThrows(
                DataConversionException.class, () -> readPatientBook("invalidPatientPatientBook.json"));
    }

    @Test
    public void readPatientBook_invalidAndValidPatientPatientBook_throwDataConversionException() {
        Assert.assertThrows(
                DataConversionException.class, () -> readPatientBook("invalidAndValidPatientPatientBook.json"));
    }

    @Test
    public void readAndSavePatientBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPatientBook.json");
        PatientBook original = TypicalPatients.getTypicalPatientBook();
        JsonPatientBookStorage jsonPatientBookStorage = new JsonPatientBookStorage(filePath);

        // Save in new file and read back
        jsonPatientBookStorage.savePatientBook(original, filePath);
        ReadOnlyPatientBook readBack = jsonPatientBookStorage.readPatientBook(filePath).get();
        assertEquals(original, new PatientBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(TypicalPatients.HOON);
        original.removePatient(TypicalPatients.ALICE);
        jsonPatientBookStorage.savePatientBook(original, filePath);
        readBack = jsonPatientBookStorage.readPatientBook(filePath).get();
        assertEquals(original, new PatientBook(readBack));

        // Save and read without specifying file path
        original.addPatient(TypicalPatients.IDA);
        jsonPatientBookStorage.savePatientBook(original); // file path not specified
        readBack = jsonPatientBookStorage.readPatientBook().get(); // file path not specified
        assertEquals(original, new PatientBook(readBack));

    }

    @Test
    public void savePatientBook_nullPatientBook_throwsNullPointerException() {
        Assert.assertThrows(
                NullPointerException.class, () -> savePatientBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code patientBook} at the specified {@code filePath}.
     */
    private void savePatientBook(ReadOnlyPatientBook patientBook, String filePath) {
        try {
            new JsonPatientBookStorage(Paths.get(filePath))
                    .savePatientBook(patientBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePatientBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> savePatientBook(new PatientBook(), null));
    }
}
