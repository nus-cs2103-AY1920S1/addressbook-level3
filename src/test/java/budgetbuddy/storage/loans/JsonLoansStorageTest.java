package budgetbuddy.storage.loans;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.loanutil.LoanBuilder;
import budgetbuddy.testutil.loanutil.TypicalDebtors;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class JsonLoansStorageTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonLoansStorageTest");

    @TempDir
    public Path testFolder;

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder) : null;
    }

    private Optional<LoansManager> readLoans(String filePath) throws DataConversionException {
        return new JsonLoansStorage(Paths.get(filePath)).readLoans(addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Saves a {@code loansManager} at the specified {@code filePath}.
     */
    private void saveLoans(LoansManager loansManager, String filePath) {
        try {
            new JsonLoansStorage(Paths.get(filePath))
                    .saveLoans(loansManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException e) {
            throw new AssertionError("There should not be an error writing to the file."
                    + "The error: " + e.getMessage(), e);
        }
    }

    @Test
    public void readLoans_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readLoans(null));
    }

    @Test
    public void readLoans_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readLoans("NonExistentFile.json").isPresent());
    }

    @Test
    public void readLoans_notJsonFile_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLoans("notJsonFormatLoansStorage.json"));
    }

    @Test
    public void readLoans_invalidLoans_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLoans("invalidLoansStorage.json"));
    }

    @Test
    public void readLoans_invalidAndValidLoans_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLoans("invalidAndValidLoansStorage.json"));
    }

    @Test
    public void saveLoans_nullLoansManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLoans(null, "JustAFile.json"));
    }

    @Test
    public void saveLoans_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLoans(new LoansManager(), null));
    }

    @Test
    public void readAndSaveLoans_allInOrder_success() throws IOException, DataConversionException {
        Path filePath = testFolder.resolve("TempLoans.json");
        LoansManager original = new LoansManager(TypicalLoans.LOAN_LIST, TypicalDebtors.DEBTOR_LIST);
        JsonLoansStorage jsonLoansStorage = new JsonLoansStorage(filePath);

        // save in new file and read back
        jsonLoansStorage.saveLoans(original, filePath);
        LoansManager readBack = jsonLoansStorage.readLoans(filePath).get();
        assertEquals(original, new LoansManager(readBack.getLoans(), readBack.getDebtors()));

        // modify data, overwrite existing file, and read back
        original.addLoan(new LoanBuilder().build());
        original.deleteLoan(TypicalIndexes.INDEX_FIRST_ITEM);
        jsonLoansStorage.saveLoans(original, filePath);
        readBack = jsonLoansStorage.readLoans(filePath).get();
        assertEquals(original, new LoansManager(readBack.getLoans(), readBack.getDebtors()));

        // save and read without specifying file path
        original.addLoan(new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID).withPerson("Duke").build());
        jsonLoansStorage.saveLoans(original); // file path not specified
        readBack = jsonLoansStorage.readLoans().get(); // file path not specified
        assertEquals(original, new LoansManager(readBack.getLoans(), readBack.getDebtors()));
    }
}
