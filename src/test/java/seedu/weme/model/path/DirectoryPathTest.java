package seedu.weme.model.path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.weme.model.meme.Description;
import seedu.weme.testutil.TestUtil;

class DirectoryPathTest {

    private static final String VALID_SANDBOX_DIRECTORY = TestUtil.getSandboxFolder().toString();
    private static final String VALID_SANDBOX_DIRECTORY_2 = TestUtil.getSecondSandboxFolder().toString();
    private static final String INVALID_SANDBOX_DIRECTORY = TestUtil.getInvalidSandboxFolder().toString();
    private static final String SAMPLE_DESCRIPTION = "Sample Description";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DirectoryPath(null));
    }

    @Test
    public void isValidDirectoryPath() {
        // null directory path
        assertThrows(NullPointerException.class, () -> DirectoryPath.isValidDirectoryPath(null));

        // valid directory path
        assertTrue(DirectoryPath.isValidDirectoryPath("")); // empty string
        assertTrue(DirectoryPath.isValidDirectoryPath(VALID_SANDBOX_DIRECTORY)); // valid directory path
        assertFalse(DirectoryPath.isValidDirectoryPath(INVALID_SANDBOX_DIRECTORY)); // invalid directory path
    }

    @Test
    public void equals() {
        final DirectoryPath directoryPath = new DirectoryPath(VALID_SANDBOX_DIRECTORY);

        // same values -> returns true
        assertTrue(directoryPath.equals(new DirectoryPath(VALID_SANDBOX_DIRECTORY)));

        // same object -> returns true
        assertTrue(directoryPath.equals(directoryPath));

        // null -> returns false
        assertFalse(directoryPath.equals(null));

        // different types -> returns false
        assertFalse(directoryPath.equals(new Description(SAMPLE_DESCRIPTION)));

        // different paths -> returns false
        assertFalse(directoryPath.equals(new DirectoryPath(VALID_SANDBOX_DIRECTORY_2)));
    }

}
