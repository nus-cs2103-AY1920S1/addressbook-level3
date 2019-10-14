package seedu.address.storage;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalCards.ALICE;
//import static seedu.address.testutil.TypicalCards.HOON;
//import static seedu.address.testutil.TypicalCards.IDA;
//import static seedu.address.testutil.TypicalCards.getTypicalWordBank;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import seedu.address.commons.exceptions.DataConversionException;
//import seedu.address.model.wordbank.WordBank;
//import seedu.address.model.wordbank.ReadOnlyWordBankList;

public class JsonWordBankStorageTest {
//    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");
//
//    @TempDir
//    public Path testFolder;
//
//    @Test
//    public void readAddressBook_nullFilePath_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> readAddressBook(null));
//    }
//
//    private java.util.Optional<ReadOnlyWordBankList> readAddressBook(String filePath) throws Exception {
//        return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
//    }
//
//    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
//        return prefsFileInTestDataFolder != null
//                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
//                : null;
//    }
//
//    @Test
//    public void read_missingFile_emptyResult() throws Exception {
//        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
//    }
//
//    @Test
//    public void read_notJsonFormat_exceptionThrown() {
//        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
//    }
//
//    @Test
//    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
//        assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
//    }
//
//    @Test
//    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
//        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
//    }
//
//    @Test
//    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
//        Path filePath = testFolder.resolve("TempAddressBook.json");
//        WordBank original = getTypicalWordBank();
//        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);
//
//        // Save in new file and read back
//        jsonAddressBookStorage.saveAddressBook(original, filePath);
//        ReadOnlyWordBankList readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
//        assertEquals(original, new WordBank(readBack));
//
//        // Modify data, overwrite exiting file, and read back
//        original.addCard(HOON);
//        original.removePerson(ALICE);
//        jsonAddressBookStorage.saveAddressBook(original, filePath);
//        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
//        assertEquals(original, new WordBank(readBack));
//
//        // Save and read without specifying file path
//        original.addCard(IDA);
//        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
//        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
//        assertEquals(original, new WordBank(readBack));
//
//    }
//
//    @Test
//    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
//    }
//
//    /**
//     * Saves {@code addressBook} at the specified {@code filePath}.
//     */
//    private void saveAddressBook(ReadOnlyWordBankList addressBook, String filePath) {
//        try {
//            new JsonAddressBookStorage(Paths.get(filePath))
//                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
//        } catch (IOException ioe) {
//            throw new AssertionError("There should not be an error writing to the file.", ioe);
//        }
//    }
//
//    @Test
//    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> saveAddressBook(new WordBank(), null));
//    }
}
