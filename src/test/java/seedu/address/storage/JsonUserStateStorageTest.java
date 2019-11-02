package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.ALICE;
import static seedu.address.testutil.TypicalTransactions.HOON;
import static seedu.address.testutil.TypicalTransactions.IDA;
// import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.UserState;

public class JsonUserStateStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths
        .get("src", "test", "data", "JsonUserStateStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUserState_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readUserState(null));
    }

    private java.util.Optional<ReadOnlyUserState> readUserState(String filePath) throws Exception {
        return new JsonUserStateStorage(Paths.get(filePath)).readUserState(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUserState("NonExistentFile.json").isPresent());
    }

    // TODO:
//    @Test
//    public void read_notJsonFormat_exceptionThrown() {
//        assertThrows(DataConversionException.class, () -> readUserState("notJsonFormatUserState.json"));
//    }

    // TODO:
//    @Test
//    public void readUserState_invalidTransactionUserState_throwDataConversionException() {
//        assertThrows(DataConversionException.class, () -> readUserState("invalidTransactionUserState.json"));
//    }

    // TODO:
//    @Test
//    public void readUserState_invalidAndValidTransactionUserState_throwDataConversionException() {
//        assertThrows(
//            DataConversionException.class, () -> readUserState("invalidAndValidTransactionUserState.json"));
//    }

    // @Test
    // public void readAndSaveUserState_allInOrder_success() throws Exception {
    //     Path filePath = testFolder.resolve("TempUserState.json");
    //     UserState original = getTypicalUserState();
    //     JsonUserStateStorage jsonUserStateStorage = new JsonUserStateStorage(filePath);
    //
    //
    //     // Save in new file and read back
    //     jsonUserStateStorage.saveUserState(original, filePath);
    //     ReadOnlyUserState readBack = jsonUserStateStorage.readUserState(filePath).get();
    //     assertEquals(original, new UserState(readBack));
    //
    //     // Modify data, overwrite exiting file, and read back
    //     original.add(HOON);
    //     original.remove(ALICE);
    //     jsonUserStateStorage.saveUserState(original, filePath);
    //     readBack = jsonUserStateStorage.readUserState(filePath).get();
    //     assertEquals(original, new UserState(readBack));
    //
    //     // Save and read without specifying file path
    //     original.add(IDA);
    //     jsonUserStateStorage.saveUserState(original); // file path not specified
    //     readBack = jsonUserStateStorage.readUserState().get(); // file path not specified
    //     assertEquals(original, new UserState(readBack));
    //
    // }

    @Test
    public void saveUserState_nullUserState_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserState(null, "SomeFile.json"));
    }

    /**
     * Saves {@code bankAccount} at the specified {@code filePath}.
     */
    private void saveUserState(ReadOnlyUserState bankAccount, String filePath) {
        try {
            new JsonUserStateStorage(Paths.get(filePath))
                .saveUserState(bankAccount, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveUserState_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserState(new UserState(), null));
    }
}
