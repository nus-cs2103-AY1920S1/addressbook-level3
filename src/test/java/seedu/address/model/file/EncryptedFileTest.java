package seedu.address.model.file;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class EncryptedFileTest {

    private EncryptedFile testFile = new EncryptedFile(
            new FileName("Test File.txt"),
            new FilePath("/Users/Shared"),
            new HashSet<>());

    @Test
    public void getFullPath() {
        assertEquals("/Users/Shared/Test File.txt", testFile.getFullPath());
    }

    @Test
    public void getEncryptedPath() {
        assertEquals("/Users/Shared/[LOCKED] Test File.txt", testFile.getEncryptedPath());
    }

    @Test
    public void getFileExtention() {
        assertEquals("txt", testFile.getFileExtension());
    }

    @Test
    public void isSameFile() {
        // same
        assertTrue(testFile.isSameFile(new EncryptedFile(
                new FileName("Test File.txt"),
                new FilePath("/Users/Shared"),
                new HashSet<>()
        )));

        // not same
        assertFalse(testFile.isSameFile(new EncryptedFile(
                new FileName("Text File.txt"),
                new FilePath("/Users/Shared"),
                new HashSet<>()
        )));
        assertFalse(testFile.isSameFile(new EncryptedFile(
                new FileName("Test File.txt"),
                new FilePath("/User/Shared"),
                new HashSet<>()
        )));
    }

}
