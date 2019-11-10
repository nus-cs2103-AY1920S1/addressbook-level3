package seedu.address.model.entity.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

//@@author shaoyi1997
class PhotoTest {

    private Photo photo = new Photo(Photo.PATH_TO_EXAMPLE_PHOTO);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Photo(null));
    }

    @Test
    public void constructor_invalidPath_throwsIllegalArgumentException() {
        String invalidPath = "/images/test.gif";
        assertThrows(IllegalArgumentException.class, () -> new Photo(invalidPath));
    }

    @Test
    void isValidPhoto_invalidPath_false() {
        // null path
        assertThrows(NullPointerException.class, () -> Photo.isValidPhoto(null));

        // invalid path
        assertFalse(Photo.isValidPhoto(""));
        assertFalse(Photo.isValidPhoto("/images/ExamplePhotos.jpg"));
        assertFalse(Photo.isValidPhoto("/images/ExamplePhoto.gif"));
    }

    @Test
    void isValidPhoto_validPath_true() {
        assertTrue(Photo.isValidPhoto(Photo.PATH_TO_EXAMPLE_PHOTO));
    }

    @Test
    void loadExamplePhoto_successful() throws IOException {
        Files.deleteIfExists(Paths.get(Photo.PATH_TO_EXAMPLE_PHOTO));
        assertEquals(photo, new Photo(Photo.PATH_TO_EXAMPLE_PHOTO));
    }

    @Test
    void toString_examplePhoto_equal() {
        assertEquals("Original Directory: " + Photo.PATH_TO_EXAMPLE_PHOTO + "\nData Directory: "
                + Photo.PATH_TO_EXAMPLE_PHOTO, photo.toString());
    }

    @Test
    void equals_samePhoto_equals() {
        Photo copyPhoto = new Photo(Photo.PATH_TO_EXAMPLE_PHOTO);
        assertEquals(photo, copyPhoto);
        assertEquals(photo.hashCode(), copyPhoto.hashCode());
    }
}
//@@author
