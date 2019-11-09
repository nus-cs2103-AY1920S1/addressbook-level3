package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DisplayPictureTest {

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String inValidFileName = "";
        assertThrows(IllegalArgumentException.class, () -> new DisplayPicture(inValidFileName));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> DisplayPicture.isValidFormat(null));

        // invalid addresses
        assertFalse(DisplayPicture.isValidFormat("")); // empty string
        assertFalse(DisplayPicture.isValidFormat(" ")); // spaces only

        // valid addresses
        assertTrue(DisplayPicture.isValidFormat("Blk 456, Den Road, #01-355"));
        assertTrue(DisplayPicture.isValidFormat("-")); // one character
        assertTrue(DisplayPicture.isValidFormat("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

}
