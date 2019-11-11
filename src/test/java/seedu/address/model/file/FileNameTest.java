package seedu.address.model.file;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FileNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FileName(null));
    }

    @Test
    public void constructor_invalidFileName_throwsIllegalArgumentException() {
        String invalidFileName = "";
        assertThrows(IllegalArgumentException.class, () -> new FileName(invalidFileName));
    }

    @Test
    public void constructWithExtension() {
        // null file name or extension
        assertThrows(NullPointerException.class, () -> FileName.constructWithExtension(null, "txt"));
        assertThrows(NullPointerException.class, () -> FileName.constructWithExtension("Test File", null));

        // with both file name and extension
        assertEquals("Test File.txt",
                FileName.constructWithExtension("Test File", "txt").value);

        // with empty file name
        assertEquals(".txt",
                FileName.constructWithExtension("", "txt").value);

        // with empty extension
        assertEquals("Test File",
                FileName.constructWithExtension("Test File", "").value);
    }

    @Test
    public void isValidFileName() {
        // valid
        assertTrue(FileName.isValidFileName("Test File"));
        assertTrue(FileName.isValidFileName(" "));

        // invalid
        assertFalse(FileName.isValidFileName(""));
    }

    @Test
    public void getFileNameWithoutExtension() {
        assertEquals("Test File", new FileName("Text File.txt").getFileNameWithoutExtention());
        assertEquals("Test File", new FileName("Text File").getFileNameWithoutExtention());
    }

    @Test
    public void getExtension() {
        assertEquals("txt", new FileName("Text File.txt").getExtension());
        assertEquals("", new FileName("Text File").getFileNameWithoutExtention());
    }
}
