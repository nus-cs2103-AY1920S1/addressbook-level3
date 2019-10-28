package seedu.address.storage.wordbanks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.getTypicalWordBank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;

public class JsonWordBankStorageTest {
    private Path testDataFolder =
            Paths.get("src", "test", "data", "JsonWordBankListStorageTest");

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
        Path filePath = testDataFolder;
        JsonWordBankListStorage jsonWordBankListStorage = new JsonWordBankListStorage(filePath);
        WordBank original = getTypicalWordBank();
        Path originalPath = Paths.get(testDataFolder.toString(), "wordBanks", getTypicalWordBank() + ".json");

        // Save in new file and read back
        jsonWordBankListStorage.saveWordBank(original);
        ReadOnlyWordBank readBack = jsonWordBankListStorage.jsonToWordBank(originalPath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.removeCard(CHARIZARD);
        jsonWordBankListStorage.saveWordBank(original);
        readBack = jsonWordBankListStorage.jsonToWordBank(originalPath).get();
        assertEquals(original, readBack);

        Path p = Paths.get(testDataFolder.toString(), "wordBanks", "sample.json");
        appendNewLine(p);
    }

    /**
     * Append new line to pass check style.
     *
     * @param p the file to append new line with.
     */
    private void appendNewLine(Path p) {
        String s = System.lineSeparator();
        try {
            Files.write(p, s.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Saves {@code WordBank} at the specified {@code filePath}.
     */
    private void saveWordBank(ReadOnlyWordBank wordBank, String filePath, String testFolder)
            throws DataConversionException, IllegalValueException {
        new JsonWordBankListStorage(testDataFolder, testFolder)
                .saveWordBank(wordBank, addToTestDataPathIfNotNull(testFolder, filePath));
    }

    private java.util.Optional<ReadOnlyWordBank> readWordBank(String filePath, String testFolder) throws Exception {
        return new JsonWordBankListStorage(testDataFolder, testFolder)
                .jsonToWordBank(addToTestDataPathIfNotNull(testFolder, filePath));
    }

    private Path addToTestDataPathIfNotNull(String testFolder, String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? testDataFolder.resolve(testFolder).resolve(prefsFileInTestDataFolder)
                : null;
    }

}
