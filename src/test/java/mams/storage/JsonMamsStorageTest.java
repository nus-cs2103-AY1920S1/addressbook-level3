package mams.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static mams.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import mams.commons.exceptions.DataConversionException;
import mams.model.Mams;
import mams.model.ReadOnlyMams;
import mams.testutil.Assert;
import mams.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class JsonMamsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMamsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMams_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readMams(null));
    }

    private java.util.Optional<ReadOnlyMams> readMams(String filePath) throws Exception {
        return new JsonMamsStorage(Paths.get(filePath)).readMams(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMams("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readMams("notJsonFormatMams.json"));
    }

    @Test
    public void readMams_invalidPersonMams_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readMams("invalidPersonMams.json"));
    }

    @Test
    public void readMams_invalidAndValidPersonMams_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readMams("invalidAndValidPersonMams.json"));
    }

    @Test
    public void readAndSaveMams_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMams.json");
        Mams original = TypicalPersons.getTypicalMams();
        JsonMamsStorage jsonMamsStorage = new JsonMamsStorage(filePath);

        // Save in new file and read back
        jsonMamsStorage.saveMams(original, filePath);
        ReadOnlyMams readBack = jsonMamsStorage.readMams(filePath).get();
        assertEquals(original, new Mams(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(TypicalPersons.HOON);
        original.removePerson(TypicalPersons.ALICE);
        jsonMamsStorage.saveMams(original, filePath);
        readBack = jsonMamsStorage.readMams(filePath).get();
        assertEquals(original, new Mams(readBack));

        // Save and read without specifying file path
        original.addPerson(TypicalPersons.IDA);
        jsonMamsStorage.saveMams(original); // file path not specified
        readBack = jsonMamsStorage.readMams().get(); // file path not specified
        assertEquals(original, new Mams(readBack));

    }

    @Test
    public void saveMams_nullMams_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveMams(null, "SomeFile.json"));
    }

    /**
     * Saves {@code mams} at the specified {@code filePath}.
     */
    private void saveMams(ReadOnlyMams mams, String filePath) {
        try {
            new JsonMamsStorage(Paths.get(filePath))
                    .saveMams(mams, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMams_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveMams(new Mams(), null));
    }
}
