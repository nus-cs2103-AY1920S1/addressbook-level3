package seedu.moneygowhere.model.path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class FilePathTest {

    private static final Path TEST_DATA_FILE = Paths.get("src", "test", "data", "invalidDateSpending.csv");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilePath(null));
    }

    @Test
    public void constructor_invalidPath_throwsIllegalArgumentException() {
        String invalidPath = "";
        assertThrows(IllegalArgumentException.class, () -> new FilePath(invalidPath));
    }

    @Test
    public void isValidPath() {
        // null address
        assertThrows(NullPointerException.class, () -> FilePath.isValidPath(null));

        // invalid addresses
        assertFalse(FilePath.isValidPath("")); // empty string
        assertFalse(FilePath.isValidPath(" ")); // spaces only

        // valid addresses
        assertTrue(FilePath.isValidPath(TEST_DATA_FILE.toString()));
    }
}
