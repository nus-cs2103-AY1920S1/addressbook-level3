package seedu.moneygowhere.model.path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class FolderPathTest {

    private static final Path TEST_DATA_FILE = Paths.get("src", "test", "data", "SampleSpendings");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FolderPath(null));
    }

    @Test
    public void constructor_invalidPath_throwsIllegalArgumentException() {
        String invalidPath = "";
        assertThrows(IllegalArgumentException.class, () -> new FolderPath(invalidPath));
    }

    @Test
    public void isValidPath() {
        // null address
        assertThrows(NullPointerException.class, () -> FolderPath.isValidFolderPath(null));

        // invalid addresses
        assertFalse(FolderPath.isValidFolderPath("")); // empty string
        assertFalse(FolderPath.isValidFolderPath(" ")); // spaces only

        // valid addresses
        assertTrue(FolderPath.isValidFolderPath(TEST_DATA_FILE.toString()));
    }
}
