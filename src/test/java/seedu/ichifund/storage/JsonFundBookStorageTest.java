package seedu.ichifund.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalPersons.ALICE;
import static seedu.ichifund.testutil.TypicalPersons.HOON;
import static seedu.ichifund.testutil.TypicalPersons.IDA;
import static seedu.ichifund.testutil.TypicalPersons.getTypicalFundBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ichifund.commons.exceptions.DataConversionException;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.ReadOnlyFundBook;

public class JsonFundBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFundBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFundBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFundBook(null));
    }

    private java.util.Optional<ReadOnlyFundBook> readFundBook(String filePath) throws Exception {
        return new JsonFundBookStorage(Paths.get(filePath)).readFundBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFundBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readFundBook("notJsonFormatFundBook.json"));
    }

    @Test
    public void readFundBook_invalidPersonFundBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFundBook("invalidPersonFundBook.json"));
    }

    @Test
    public void readFundBook_invalidAndValidPersonFundBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFundBook("invalidAndValidPersonFundBook.json"));
    }

    @Test
    public void readAndSaveFundBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempFundBook.json");
        FundBook original = getTypicalFundBook();
        JsonFundBookStorage jsonFundBookStorage = new JsonFundBookStorage(filePath);

        // Save in new file and read back
        jsonFundBookStorage.saveFundBook(original, filePath);
        ReadOnlyFundBook readBack = jsonFundBookStorage.readFundBook(filePath).get();
        assertEquals(original, new FundBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonFundBookStorage.saveFundBook(original, filePath);
        readBack = jsonFundBookStorage.readFundBook(filePath).get();
        assertEquals(original, new FundBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonFundBookStorage.saveFundBook(original); // file path not specified
        readBack = jsonFundBookStorage.readFundBook().get(); // file path not specified
        assertEquals(original, new FundBook(readBack));

    }

    @Test
    public void saveFundBook_nullFundBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFundBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code fundBook} at the specified {@code filePath}.
     */
    private void saveFundBook(ReadOnlyFundBook fundBook, String filePath) {
        try {
            new JsonFundBookStorage(Paths.get(filePath))
                    .saveFundBook(fundBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFundBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFundBook(new FundBook(), null));
    }
}
