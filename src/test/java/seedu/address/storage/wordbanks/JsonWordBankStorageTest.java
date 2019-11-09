// @@author chrischenhui
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

class JsonWordBankStorageTest {
    private Path testDataFolder =
            Paths.get("src", "test", "data", "JsonWordBankListStorageTest");

    @Test
    void readWordBank_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWordBank(null, "firstTest"));
    }

    @Test
    void read_nonExistentFile_emptyResult() throws Exception {
        assertFalse(readWordBank("NonExistentFile.json", "secondTest").isPresent());
    }

    @Test
    void read_notJsonWordBankFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readWordBank("notJsonFormatWordBank.json", "thirdTest"));
    }

    @Test
    void readWordBank_invalidWordBank_throwIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> readWordBank("invalidWordBank.json", "fourthTest"));
    }

    @Test
    void readWordBank_invalidAndValidCardsWordBank_throwIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> readWordBank("invalidAndValidCardsWordBank.json", "fifthTest"));
    }

    @Test
    void saveWordBank_nullWordBank_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWordBank(null, "SomeFile.json", "sixthTest"));
    }

    @Test
    void saveWordBank_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWordBank(new WordBank("test"), null, "seventhTest"));
    }

    @Test
    void readAndSaveWordBank_allInOrder_success() throws Exception {
        Path filePath = testDataFolder;
        JsonWordBankListStorage jsonWordBankListStorage = new JsonWordBankListStorage(filePath, false);
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

        Path p1 = Paths.get(testDataFolder.toString(), "wordBanks", "pokemon.json");
        appendNewLine(p1);
        Path p2 = Paths.get(testDataFolder.toString(), "wordBanks", "arithmetic.json");
        appendNewLine(p2);
        Path p3 = Paths.get(testDataFolder.toString(), "wordBanks", "trivia.json");
        appendNewLine(p3);
        Path p4 = Paths.get(testDataFolder.toString(), "wordBanks", "sample.json");
        appendNewLine(p4);
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
        new JsonWordBankListStorage(testDataFolder, testFolder, true)
                .saveWordBank(wordBank, addToTestDataPathIfNotNull(testFolder, filePath));
    }

    private java.util.Optional<ReadOnlyWordBank> readWordBank(String filePath, String testFolder) throws Exception {
        return new JsonWordBankListStorage(testDataFolder, testFolder, true)
                .jsonToWordBank(addToTestDataPathIfNotNull(testFolder, filePath));
    }

    private Path addToTestDataPathIfNotNull(String testFolder, String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? testDataFolder.resolve(testFolder).resolve(prefsFileInTestDataFolder)
                : null;
    }

}
