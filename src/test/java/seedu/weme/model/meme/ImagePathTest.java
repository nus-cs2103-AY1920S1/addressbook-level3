package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.model.path.ImagePath;

public class ImagePathTest extends ApplicationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImagePath(null));
    }

    @Test
    public void constructor_invalidFilePath_throwsIllegalArgumentException() {
        String invalidUrl = "hello";
        assertThrows(IllegalArgumentException.class, () -> new ImagePath(invalidUrl));
    }

    @Test
    public void isValidFilePath() {
        // null file path
        assertThrows(NullPointerException.class, () -> ImagePath.isValidFilePath(null));

        // invalid file paths
        assertFalse(ImagePath.isValidFilePath("hello")); // invalid file path
        assertFalse(ImagePath.isValidFilePath(" ")); // spaces only

        // valid file path
        assertTrue(ImagePath.isValidFilePath("src/test/data/memes/charmander_meme.jpg"));
    }
}
