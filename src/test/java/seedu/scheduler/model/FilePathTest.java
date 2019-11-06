package seedu.scheduler.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FilePathTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilePath(null));
    }

    @Test
    public void constructor_invalidFile_throwsIllegalArgumentException() {
        String invalidFile = "";
        assertThrows(IllegalArgumentException.class, () -> new FilePath(invalidFile));
    }

    @Test
    public void isValidFilePath() {
        //Valid FilePaths
        assertTrue(FilePath.isValidFilePath("storage.csv")); // alphabets only
        assertTrue(FilePath.isValidFilePath("12345.csv")); // numbers only
        assertTrue(FilePath.isValidFilePath("12345abc.csv")); // numbers and letters only
        assertTrue(FilePath.isValidFilePath("1.2.3.4.5.csv")); // with dots
        assertTrue(FilePath.isValidFilePath("1/2/3/4/5.csv")); // with slashes

        //Invalid FilePaths
        assertFalse(FilePath.isValidFilePath("")); // blank name
        assertFalse(FilePath.isValidFilePath(".csv")); // blank name with .csv
        assertFalse(FilePath.isValidFilePath("1/2/3/4/5/csv")); // no .csv extension
        assertFalse(FilePath.isValidFilePath("storage.xlsx")); // no .csv extension
        assertFalse(FilePath.isValidFilePath("abc.csv/abc.com")); // no .csv extension

        // null name
        assertThrows(NullPointerException.class, () -> FilePath.isValidFilePath(null));
    }
}
