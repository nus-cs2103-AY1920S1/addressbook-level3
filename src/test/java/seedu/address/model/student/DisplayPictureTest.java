package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DisplayPictureTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DisplayPicture(null));
    }

    @Test
    public void isValidFormat() {
        // null DisplayPicture
        assertThrows(NullPointerException.class, () -> DisplayPicture.isValidFormat(null));

        // invalid DisplayPictures
        assertFalse(DisplayPicture.isValidFormat("")); // empty string
        assertFalse(DisplayPicture.isValidFormat(" ")); // spaces only
        assertFalse(DisplayPicture.isValidFormat("D://")); // file length less than 5
        assertFalse(DisplayPicture.isValidFormat("/Users/file.pdf")); // file is not jpg or png format

        // valid DisplayPictures
        assertTrue(DisplayPicture.isValidFormat("/Users/file.png")); // png format
        assertTrue(DisplayPicture.isValidFormat("/Users/user/picture.jpg")); // jpg format
        assertTrue(DisplayPicture.isValidFormat("/Users/user/Desktop/CS2103/TeamProject/Folder/file.jpg")); // long DisplayPicture
    }
}
