package seedu.savenus.storage.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalWallet.getTypicalWallet;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;


//@@author raikonen
public class JsonWalletStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonWalletStorageTest");

    @TempDir
    public Path testFolder;

    private java.util.Optional<Wallet> readWallet(String filePath) throws Exception {
        return new JsonWalletStorage(Paths.get(filePath))
                .readWallet(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readWallet_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWallet(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWallet("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(
                DataConversionException.class, () -> readWallet("notJsonFormatWallet.json"));
    }

    @Test
    public void readAndSaveWallet_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMenu.json");
        Wallet original = getTypicalWallet();
        JsonWalletStorage jsonWalletStorage = new JsonWalletStorage(filePath);

        // Save in new file and read back
        jsonWalletStorage.saveWallet(original, filePath);
        Wallet readBack = jsonWalletStorage.readWallet(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.setRemainingBudget(new RemainingBudget("200"));
        original.setDaysToExpire(new DaysToExpire("50"));
        jsonWalletStorage.saveWallet(original, filePath);
        readBack = jsonWalletStorage.readWallet(filePath).get();
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.setRemainingBudget(new RemainingBudget("200"));
        original.setDaysToExpire(new DaysToExpire("50"));
        jsonWalletStorage.saveWallet(original); // file path not specified
        readBack = jsonWalletStorage.readWallet().get(); // file path not specified
        assertEquals(original, readBack);
    }

    @Test
    public void saveMenu_nullWallet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWallet(null, "SomeFile.json"));
    }

    /**
     * Saves {@code Wallet} at the specified {@code filePath}.
     */
    private void saveWallet(Wallet wallet, String filePath) {
        try {
            new JsonWalletStorage(Paths.get(filePath))
                    .saveWallet(wallet, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRecs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWallet(new Wallet(), null));
    }

}
