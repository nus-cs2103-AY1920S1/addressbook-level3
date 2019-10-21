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
//        assertThrows(NullPointerException.class, () -> getWordBank(null));
//    }
//
//    private java.util.Optional<ReadOnlyWordBankList> getWordBank(String filePath) throws Exception {
//        return new JsonWordBankListStorage(Paths.get(filePath)).getWordBank(addToTestDataPathIfNotNull(filePath));
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
//        assertFalse(getWordBank("NonExistentFile.json").isPresent());
//    }
//
//    @Test
//    public void read_notJsonFormat_exceptionThrown() {
//        assertThrows(DataConversionException.class, () -> getWordBank("notJsonFormatAddressBook.json"));
//    }
//
//    @Test
//    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
//        assertThrows(DataConversionException.class, () -> getWordBank("invalidPersonAddressBook.json"));
//    }
//
//    @Test
//    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
//        assertThrows(DataConversionException.class, () -> getWordBank("invalidAndValidPersonAddressBook.json"));
//    }
//
//    @Test
//    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
//        Path filePath = testFolder.resolve("TempAddressBook.json");
//        WordBank original = getTypicalWordBank();
//        JsonWordBankListStorage jsonAddressBookStorage = new JsonWordBankListStorage(filePath);
//
//        // Save in new file and read back
//        jsonAddressBookStorage.saveWordBanks(original, filePath);
//        ReadOnlyWordBankList readBack = jsonAddressBookStorage.getWordBank(filePath).get();
//        assertEquals(original, new WordBank(readBack));
//
//        // Modify data, overwrite exiting file, and read back
//        original.addBank(HOON);
//        original.removePerson(ALICE);
//        jsonAddressBookStorage.saveWordBanks(original, filePath);
//        readBack = jsonAddressBookStorage.getWordBank(filePath).get();
//        assertEquals(original, new WordBank(readBack));
//
//        // Save and read without specifying file path
//        original.addBank(IDA);
//        jsonAddressBookStorage.saveWordBanks(original); // file path not specified
//        readBack = jsonAddressBookStorage.getWordBank().get(); // file path not specified
//        assertEquals(original, new WordBank(readBack));
//
//    }
//
//    @Test
//    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> saveWordBanks(null, "SomeFile.json"));
//    }
//
//    /**
//     * Saves {@code addressBook} at the specified {@code filePath}.
//     */
//    private void saveWordBanks(ReadOnlyWordBankList addressBook, String filePath) {
//        try {
//            new JsonWordBankListStorage(Paths.get(filePath))
//                    .saveWordBanks(addressBook, addToTestDataPathIfNotNull(filePath));
//        } catch (IOException ioe) {
//            throw new AssertionError("There should not be an error writing to the file.", ioe);
//        }
//    }
//
//    @Test
//    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> saveWordBanks(new WordBank(), null));
//    }
}
