package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFlashCards.GREETING;
import static seedu.address.testutil.TypicalFlashCards.STORE_AND_FORWARD;
import static seedu.address.testutil.TypicalFlashCards.SUM;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.ReadOnlyKeyboardFlashCards;

public class JsonKeyboardFlashCardsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonKeyboardFlashCardsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readKeyboardFlashCards(null));
    }

    private java.util.Optional<ReadOnlyKeyboardFlashCards> readKeyboardFlashCards (String filePath) throws Exception {
        return new JsonKeyboardFlashCardsStorage(
                Paths.get(filePath)).readKeyboardFlashCards(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readKeyboardFlashCards("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(
                DataConversionException.class, () -> readKeyboardFlashCards("notJsonFormatKeyboardFlashCards.json"));
    }

    @Test
    public void readAddressBook_invalidFlashCardAddressBook_throwDataConversionException() {
        assertThrows(
                DataConversionException.class, () -> readKeyboardFlashCards("invalidFlashCardKeyboardFlashCards.json"));
    }

    @Test
    public void readKeyboardFlashCards_invalidAndValidKeyboardFlashCards_throwDataConversionException() {
        assertThrows(
                DataConversionException.class, () -> readKeyboardFlashCards(
                        "invalidAndValidFlashCardKeyboardFlashCards.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempKeyboardFlashCards.json");
        KeyboardFlashCards original = getTypicalAddressBook();
        JsonKeyboardFlashCardsStorage jsonAddressBookStorage = new JsonKeyboardFlashCardsStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyKeyboardFlashCards readBack = jsonAddressBookStorage.readKeyboardFlashCards(filePath).get();
        assertEquals(original, new KeyboardFlashCards(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFlashcard(GREETING);
        original.removeFlashCard(STORE_AND_FORWARD);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readKeyboardFlashCards(filePath).get();
        assertEquals(original, new KeyboardFlashCards(readBack));

        // Save and read without specifying file path
        original.addFlashcard(SUM);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readKeyboardFlashCards().get(); // file path not specified
        assertEquals(original, new KeyboardFlashCards(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyKeyboardFlashCards addressBook, String filePath) {
        try {
            new JsonKeyboardFlashCardsStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new KeyboardFlashCards(), null));
    }
}
