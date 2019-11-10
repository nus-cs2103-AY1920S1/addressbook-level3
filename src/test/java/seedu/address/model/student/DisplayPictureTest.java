package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DisplayPictureTest {

    @Test
    public void constructor_invalidDisplayPicture_throwsIllegalArgumentException() {
        String inValidFileName = "";
        assertThrows(IllegalArgumentException.class, () -> new DisplayPicture(inValidFileName));
    }


    @Test
    public void isValidDisplayPicture() {
        // null filename
        assertThrows(NullPointerException.class, () -> DisplayPicture.isValidFormat(null));

        // invalid filenames

        assertFalse(DisplayPicture.isValidFormat("")); // empty string
        assertFalse(DisplayPicture.isValidFormat(" ")); // spaces only
        assertFalse(DisplayPicture.isValidFormat("asddf"));
        assertFalse(DisplayPicture.isValidFormat("asddf234567uio"));
        assertFalse(DisplayPicture.isValidFormat("D://")); // file length less than 5
        assertFalse(DisplayPicture.isValidFormat("/Users/file.pdf")); // file is not jpg or png format

        // valid filename
        assertTrue(DisplayPicture.isValidFormat("/images/themyth.png"));
        assertTrue(DisplayPicture.isValidFormat("file:/D:/IMG-20190206-WA0010.jpg")); // actual file
    }
}
