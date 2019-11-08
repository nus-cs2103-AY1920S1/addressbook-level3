package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PictureTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Picture(null));
    }

    @Test
    public void constructor_invalidPicture_throwsIllegalArgumentException() {
        String invalidPicture = "";
        assertThrows(IllegalArgumentException.class, () -> new Picture(invalidPicture));
    }

    @Test
    public void constructor_invalidPictureFormat_throwsIllegalArgumentException() {
        String invalidPicture = "test.tif";
        assertThrows(IllegalArgumentException.class, () -> new Picture(invalidPicture));
    }

    @Test
    public void isValidPicture() {
        // null picture
        assertThrows(NullPointerException.class, () -> Picture.isValidPicture(null));

        // invalid pictures
        assertFalse(Picture.isValidPicture("")); // empty string
        assertFalse(Picture.isValidPicture(" ")); // spaces only

        // valid pictures
        assertTrue(Picture.isValidPicture("test.jpg"));
        assertTrue(Picture.isValidPicture("123.jpg"));
        assertTrue(Picture.isValidPicture("21!.jpg"));
    }
}
