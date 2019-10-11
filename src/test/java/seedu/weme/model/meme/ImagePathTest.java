package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ImagePathTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImagePath(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidUrl = "hello";
        assertThrows(IllegalArgumentException.class, () -> new ImagePath(invalidUrl));
    }

    @Test
    public void isValidFilePath() {
        // null weme
        assertThrows(NullPointerException.class, () -> ImagePath.isValidFilePath(null));

        // invalid addresses
        assertFalse(ImagePath.isValidFilePath("hello")); // invalid file path
        assertFalse(ImagePath.isValidFilePath(" ")); // spaces only

        // valid addresses
        assertTrue(ImagePath.isValidFilePath("src/test/data/memes/charmander_meme.jpg"));
    }
}
