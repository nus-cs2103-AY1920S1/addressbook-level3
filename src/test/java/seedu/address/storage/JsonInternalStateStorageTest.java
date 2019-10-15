package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.InternalState;

public class JsonInternalStateStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonInternalStateStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readInternalState_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readInternalState(null));
    }

    private Optional<InternalState> readInternalState(String internalStateFileInTestDataFolder)
            throws DataConversionException {
        Path internalStateFilePath = addToTestDataPathIfNotNull(internalStateFileInTestDataFolder);
        return new JsonInternalStateStorage(internalStateFilePath).readInternalState(internalStateFilePath);
    }

    @Test
    public void readInternalState_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readInternalState("NonExistentFile.json").isPresent());
    }

    @Test
    public void readInternalState_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readInternalState("NotJsonFormattedInternalState.json"));
    }

    private Path addToTestDataPathIfNotNull(String internalStateFileInTestDataFolder) {
        return internalStateFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(internalStateFileInTestDataFolder)
                : null;
    }

    @Test
    public void readInternalState_fileInOrder_successfullyRead() throws DataConversionException {
        InternalState expected = getTypicalInternalState();
        InternalState actual = readInternalState("TypicalInternalState.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readInternalState_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        InternalState actual = readInternalState("EmptyInternalState.json").get();
        assertEquals(new InternalState(), actual);
    }

    @Test
    public void readInternalState_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        InternalState expected = getTypicalInternalState();
        InternalState actual = readInternalState("ExtraValueInternalState.json").get();

        assertEquals(expected, actual);
    }

    private InternalState getTypicalInternalState() {
        return new InternalState();
    }

    @Test
    public void saveState_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveState(null, "SomeFile.json"));
    }

    @Test
    public void saveState_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveState(new InternalState(), null));
    }

    /**
     * Saves {@code internalState} at the specified {@code stateFileInTestDataFolder} filepath.
     */
    private void saveState(InternalState internalState, String stateFileInTestDataFolder) {
        try {
            new JsonInternalStateStorage(addToTestDataPathIfNotNull(stateFileInTestDataFolder))
                    .saveInternalState(internalState);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveInternalState_allInOrder_success() throws DataConversionException, IOException {

        InternalState original = new InternalState();

        Path pefsFilePath = testFolder.resolve("TempState.json");
        JsonInternalStateStorage jsonInternalStateStorage = new JsonInternalStateStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonInternalStateStorage.saveInternalState(original);
        InternalState readBack = jsonInternalStateStorage.readInternalState().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        jsonInternalStateStorage.saveInternalState(original);
        readBack = jsonInternalStateStorage.readInternalState().get();
        assertEquals(original, readBack);
    }

}
