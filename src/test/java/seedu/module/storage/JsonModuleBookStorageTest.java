// package seedu.module.storage;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static seedu.module.testutil.Assert.assertThrows;
// import static seedu.module.testutil.TypicalPersons.ALICE;
// import static seedu.module.testutil.TypicalPersons.HOON;
// import static seedu.module.testutil.TypicalPersons.IDA;
// import static seedu.module.testutil.TypicalPersons.getTypicalAddressBook;

// import java.io.IOException;
// import java.nio.file.Path;
// import java.nio.file.Paths;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.io.TempDir;

// import seedu.module.commons.exceptions.DataConversionException;
// import seedu.module.model.ModuleBook;
// import seedu.module.model.ReadOnlyModuleBook;

// public class JsonModuleBookStorageTest {
//     private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

//     @TempDir
//     public Path testFolder;

//     @Test
//     public void readAddressBook_nullFilePath_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> readAddressBook(null));
//     }

//     private java.util.Optional<ReadOnlyModuleBook> readAddressBook(String filePath) throws Exception {
//         return new JsonModuleBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
//     }

//     private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
//         return prefsFileInTestDataFolder != null
//                 ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
//                 : null;
//     }

//     @Test
//     public void read_missingFile_emptyResult() throws Exception {
//         assertFalse(readAddressBook("NonExistentFile.json").isPresent());
//     }

//     @Test
//     public void read_notJsonFormat_exceptionThrown() {
//         assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
//     }

//     @Test
//     public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
//         assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
//     }

//     @Test
//     public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
//         assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
//     }

//     @Test
//     public void readAndSaveAddressBook_allInOrder_success() throws Exception {
//         Path filePath = testFolder.resolve("TempAddressBook.json");
//         ModuleBook original = getTypicalAddressBook();
//         JsonModuleBookStorage jsonModuleBookStorage = new JsonModuleBookStorage(filePath);

//         // Save in new file and read back
//         jsonModuleBookStorage.saveAddressBook(original, filePath);
//         ReadOnlyModuleBook readBack = jsonModuleBookStorage.readAddressBook(filePath).get();
//         assertEquals(original, new ModuleBook(readBack));

//         // Modify data, overwrite exiting file, and read back
//         original.addModule(HOON);
//         original.removePerson(ALICE);
//         jsonModuleBookStorage.saveAddressBook(original, filePath);
//         readBack = jsonModuleBookStorage.readAddressBook(filePath).get();
//         assertEquals(original, new ModuleBook(readBack));

//         // Save and read without specifying file path
//         original.addModule(IDA);
//         jsonModuleBookStorage.saveAddressBook(original); // file path not specified
//         readBack = jsonModuleBookStorage.readAddressBook().get(); // file path not specified
//         assertEquals(original, new ModuleBook(readBack));

//     }

//     @Test
//     public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
//     }

//     /**
//      * Saves {@code addressBook} at the specified {@code filePath}.
//      */
//     private void saveAddressBook(ReadOnlyModuleBook addressBook, String filePath) {
//         try {
//             new JsonModuleBookStorage(Paths.get(filePath))
//                     .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
//         } catch (IOException ioe) {
//             throw new AssertionError("There should not be an error writing to the file.", ioe);
//         }
//     }

//     @Test
//     public void saveAddressBook_nullFilePath_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> saveAddressBook(new ModuleBook(), null));
//     }
// }
