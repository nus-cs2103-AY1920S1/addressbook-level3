package seedu.mark.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class FolderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Folder(null));
    }

    @Test
    public void constructor_invalidFolderName_throwsIllegalArgumentException() {
        String invalidFolderName = "!@#$";
        assertThrows(IllegalArgumentException.class, () -> new Folder(invalidFolderName));
    }

    @Test
    public void isValidFolder() {
        // null folder
        assertThrows(NullPointerException.class, () -> Folder.isValidFolder(null));

        // invalid folder
        assertFalse(Folder.isValidFolder("")); // empty string
        assertFalse(Folder.isValidFolder(" ")); // spaces only
        assertFalse(Folder.isValidFolder("^")); // only non-alphanumeric characters
        assertFalse(Folder.isValidFolder("peter*")); // contains non-alphanumeric characters
        assertFalse(Folder.isValidFolder("peter pan")); // contains spaces

        // valid name
        assertTrue(Folder.isValidFolder("peterjack")); // alphabets only
        assertTrue(Folder.isValidFolder("12345")); // numbers only
        assertTrue(Folder.isValidFolder("peterthe2nd")); // alphanumeric characters
        assertTrue(Folder.isValidFolder("CapitalTan")); // with capital letters
        assertTrue(Folder.isValidFolder("123456789101112ThirteenFourteenFifteen")); // long names
    }
}
