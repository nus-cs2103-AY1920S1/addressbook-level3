package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.ALICE;
import static seedu.address.testutil.TypicalTransactions.HOON;
import static seedu.address.testutil.TypicalTransactions.IDA;
import static seedu.address.testutil.TypicalTransactions.getTypicalBankAccount;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BankAccount;
import seedu.address.model.ReadOnlyBankAccount;


public class JsonBankAccountStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths
        .get("src", "test", "data", "JsonBankAccountStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readBankAccount_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readBankAccount(null));
    }

    private java.util.Optional<ReadOnlyBankAccount> readBankAccount(String filePath) throws Exception {
        return new JsonBankAccountStorage(Paths.get(filePath)).readAccount(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBankAccount("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readBankAccount("notJsonFormatBankAccount.json"));
    }

    @Test
    public void readBankAccount_invalidTransactionBankAccount_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBankAccount("invalidTransactionBankAccount.json"));
    }

    @Test
    public void readBankAccount_invalidAndValidTransactionBankAccount_throwDataConversionException() {
        assertThrows(
            DataConversionException.class, () -> readBankAccount("invalidAndValidTransactionBankAccount.json"));
    }

    @Test
    public void readAndSaveBankAccount_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempBankAccount.json");
        BankAccount original = getTypicalBankAccount();
        JsonBankAccountStorage jsonBankAccountStorage = new JsonBankAccountStorage(filePath);


        // Save in new file and read back
        jsonBankAccountStorage.saveAccount(original, filePath);
        ReadOnlyBankAccount readBack = jsonBankAccountStorage.readAccount(filePath).get();
        assertEquals(original, new BankAccount(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTransaction(HOON);
        original.removeTransaction(ALICE);
        jsonBankAccountStorage.saveAccount(original, filePath);
        readBack = jsonBankAccountStorage.readAccount(filePath).get();
        assertEquals(original, new BankAccount(readBack));

        // Save and read without specifying file path
        original.addTransaction(IDA);
        jsonBankAccountStorage.saveAccount(original); // file path not specified
        readBack = jsonBankAccountStorage.readAccount().get(); // file path not specified
        assertEquals(original, new BankAccount(readBack));

    }

    @Test
    public void saveBankAccount_nullBankAccount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBankAccount(null, "SomeFile.json"));
    }

    /**
     * Saves {@code bankAccount} at the specified {@code filePath}.
     */
    private void saveBankAccount(ReadOnlyBankAccount bankAccount, String filePath) {
        try {
            new JsonBankAccountStorage(Paths.get(filePath))
                .saveAccount(bankAccount, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveBankAccount_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBankAccount(new BankAccount(), null));
    }
}
