package seedu.algobase.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.algobase.commons.exceptions.DataConversionException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.ReadOnlyAlgoBase;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.*;

public class JsonAlgoBaseStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAlgoBaseStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAlgoBase_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAlgoBase(null));
    }

    private java.util.Optional<ReadOnlyAlgoBase> readAlgoBase(String filePath) throws Exception {
        return new JsonAlgoBaseStorage(Paths.get(filePath)).readAlgoBase(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAlgoBase("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAlgoBase("notJsonFormatAlgoBase.json"));
    }

    @Test
    public void readAlgoBase_invalidProblemAlgoBase_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAlgoBase("invalidProblemAlgoBase.json"));
    }

    @Test
    public void readAlgoBase_invalidAndValidProblemAlgoBase_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAlgoBase("invalidAndValidProblemAlgoBase.json"));
    }

    @Test
    public void readAndSaveAlgoBase_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAlgoBase.json");
        AlgoBase original = getTypicalAlgoBase();
        JsonAlgoBaseStorage jsonAlgoBaseStorage = new JsonAlgoBaseStorage(filePath);

        // Save in new file and read back
        jsonAlgoBaseStorage.saveAlgoBase(original, filePath);
        ReadOnlyAlgoBase readBack = jsonAlgoBaseStorage.readAlgoBase(filePath).get();
        assertEquals(original, new AlgoBase(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addProblem(HOON);
        original.removeProblem(ALICE);
        jsonAlgoBaseStorage.saveAlgoBase(original, filePath);
        readBack = jsonAlgoBaseStorage.readAlgoBase(filePath).get();
        assertEquals(original, new AlgoBase(readBack));

        // Save and read without specifying file path
        original.addProblem(IDA);
        jsonAlgoBaseStorage.saveAlgoBase(original); // file path not specified
        readBack = jsonAlgoBaseStorage.readAlgoBase().get(); // file path not specified
        assertEquals(original, new AlgoBase(readBack));

    }

    @Test
    public void saveAlgoBase_nullAlgoBase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAlgoBase(null, "SomeFile.json"));
    }

    /**
     * Saves {@code algoBase} at the specified {@code filePath}.
     */
    private void saveAlgoBase(ReadOnlyAlgoBase algoBase, String filePath) {
        try {
            new JsonAlgoBaseStorage(Paths.get(filePath))
                    .saveAlgoBase(algoBase, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAlgoBase_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAlgoBase(new AlgoBase(), null));
    }
}
