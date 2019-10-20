package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.sorter.CustomSorter;

public class JsonCustomSortStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src",
            "test", "data", "JsonCustomSortStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFields_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFields(null));
    }

    private java.util.Optional<CustomSorter> readFields(String filePath) throws Exception {
        return new JsonCustomSortStorage(Paths.get(filePath)).readFields(addToTestDataPathIfNotNull(filePath));
    }

    private java.util.Optional<CustomSorter> readFields() throws Exception {
        return new JsonCustomSortStorage(TEST_DATA_FOLDER).readFields();
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFields("NonExistentFile.json").isPresent());
    }

    @Test
    public void dataConversion_error() {
        assertThrows(DataConversionException.class, () -> readFields());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readFields("notJsonFormatCustomSort.json"));
    }

    @Test
    public void readMenu_invalidAndValidRecs_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFields("invalidCustomSorter.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveFields(CustomSorter sort, String filePath) {
        try {
            new JsonCustomSortStorage(Paths.get(filePath))
                    .saveFields(sort, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }
    /**
     * Saves {@code addressBook}.
     */
    private void saveFields(CustomSorter sort) {
        try {
            new JsonCustomSortStorage(TEST_DATA_FOLDER)
                    .saveFields(sort);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFields_nullFilePath_throwsNullPointerException() {
        CustomSorter sort = null;
        assertThrows(NullPointerException.class, () -> saveFields(new CustomSorter(), null));
        assertThrows(NullPointerException.class, () -> saveFields(sort));
    }
}
