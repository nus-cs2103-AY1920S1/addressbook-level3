package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PhotoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Photo(null));
    }

    @Test
    public void constructor_invalidPhoto_throwsIllegalArgumentException() {
        String invalidPhoto = "@dummy.png";
        assertThrows(IllegalArgumentException.class, () -> new Photo(invalidPhoto));
    }

    @Test
    public void isValidPhoto() {
        assertThrows(NullPointerException.class, () -> Photo.isValidFilePath(null));

        assertFalse(Photo.isValidFilePath(""));
        assertFalse(Photo.isValidFilePath(" "));
        assertFalse(Photo.isValidFilePath("!@#dummy.png"));
        assertFalse(Photo.isValidFilePath("21"));
        assertFalse(Photo.isValidFilePath("dummy.jpeg"));
        assertFalse(Photo.isValidFilePath("Chicken.jpg"));

        assertTrue(Photo.isValidFilePath("12345.png"));
        assertTrue(Photo.isValidFilePath("Default.png"));
        assertTrue(Photo.isValidFilePath("bobby97.png"));
        assertTrue(Photo.isValidFilePath("986amyBoseman.png"));
    }
}
