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
        assertThrows(NullPointerException.class, () -> toModelType(null));
    }

    @Test
    public void read_nonExistentFile_emptyResult() throws Exception {
        assertFalse(toModelType("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonWordBankFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> toModelType("notJsonFormatWordBank.json"));
    }

    @Test
    public void readWordBank_invalidWordBank_throwIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> toModelType("invalidWordBank.json"));
    }

    @Test
    public void readWordBank_invalidAndValidCardsWordBank_throwIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> toModelType("invalidAndValidCardsWordBank.json"));
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
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.removeCard(CHARIZARD);
        jsonWordBankListStorage.saveWordBank(original);
        readBack = jsonWordBankListStorage.jsonToWordBank(originalPath).get();
        assertEquals(original, readBack);
    }


    @Test
    public void saveWordBank_nullWordBank_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWordBanks(null, "SomeFile.json"));
    }

    @Test
    public void saveWordBank_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWordBanks(new WordBank("test"), null));
    }

    /**
     * Saves {@code WordBank} at the specified {@code filePath}.
     */
    private void saveWordBanks(ReadOnlyWordBank wordBank, String filePath) {
        //        try {
        new JsonWordBankListStorage(Paths.get(filePath))
                .saveWordBank(wordBank, addToTestDataPathIfNotNull(filePath));
        //        } catch (IOException ioe) {
        //            throw new AssertionError("There should not be an error writing to the file.", ioe);
        //        }
    }

    private java.util.Optional<ReadOnlyWordBank> toModelType(String filePath) throws Exception {
        return new JsonWordBankListStorage(Paths.get(filePath)).jsonToWordBank(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

}
