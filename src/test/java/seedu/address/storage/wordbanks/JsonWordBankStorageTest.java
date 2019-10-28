package seedu.address.storage.wordbanks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.getTypicalWordBank;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;

public class JsonWordBankStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonWordBankListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readWordBank_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWordBank(null, "firstTest"));
    }

    @Test
    public void read_nonExistentFile_emptyResult() throws Exception {
        assertFalse(readWordBank("NonExistentFile.json", "secondTest").isPresent());
    }

    @Test
    public void read_notJsonWordBankFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readWordBank("notJsonFormatWordBank.json", "thirdTest"));
    }

    @Test
    public void readWordBank_invalidWordBank_throwIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> readWordBank("invalidWordBank.json", "fourthTest"));
    }

    @Test
    public void readWordBank_invalidAndValidCardsWordBank_throwIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> readWordBank("invalidAndValidCardsWordBank.json", "fifthTest"));
    }

    @Test
    public void saveWordBank_nullWordBank_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWordBank(null, "SomeFile.json", "sixthTest"));
    }

    @Test
    public void saveWordBank_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWordBank(new WordBank("test"), null, "seventhTest"));
    }

    @Test
    public void readAndSaveWordBank_allInOrder_success() throws Exception {
        Path filePath = TEST_DATA_FOLDER;
        JsonWordBankListStorage jsonWordBankListStorage = new JsonWordBankListStorage(filePath);
        WordBank original = getTypicalWordBank();
        Path originalPath = Paths.get(TEST_DATA_FOLDER.toString(), "wordBanks", getTypicalWordBank() + ".json");

        // Save in new file and read back
        jsonWordBankListStorage.saveWordBank(original);
        ReadOnlyWordBank readBack = jsonWordBankListStorage.jsonToWordBank(originalPath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.removeCard(CHARIZARD);
        jsonWordBankListStorage.saveWordBank(original);
        readBack = jsonWordBankListStorage.jsonToWordBank(originalPath).get();
        assertEquals(original, readBack);
    }

    /**
     * Saves {@code WordBank} at the specified {@code filePath}.
     */
    private void saveWordBank(ReadOnlyWordBank wordBank, String filePath, String testFolder)
            throws DataConversionException, IllegalValueException {
        new JsonWordBankListStorage(TEST_DATA_FOLDER, testFolder)
                .saveWordBank(wordBank, addToTestDataPathIfNotNull(testFolder, filePath));
    }

    private java.util.Optional<ReadOnlyWordBank> readWordBank(String filePath, String testFolder) throws Exception {
        return new JsonWordBankListStorage(TEST_DATA_FOLDER, testFolder)
                .jsonToWordBank(addToTestDataPathIfNotNull(testFolder, filePath));
    }

    private Path addToTestDataPathIfNotNull(String testFolder, String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(testFolder).resolve(prefsFileInTestDataFolder)
                : null;
    }

}
