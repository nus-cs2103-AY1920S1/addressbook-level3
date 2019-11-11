package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void isFileExist() {
        assertTrue(FileUtil.isFileExists(Path.of("src/test/data/ApiStubsTest/GmapsPlaceDetailsOK.json")));
    }

    @Test
    public void createIfMissing_filePresent() {
        assertDoesNotThrow(() ->FileUtil.createIfMissing(Path.of(
                "src/test/data/ApiStubsTest/GmapsPlaceDetailsOK.json")));
    }

    @Test
    public void createFile_filePresent() {
        assertDoesNotThrow(() ->FileUtil.createFile(Path.of(
                "src/test/data/ApiStubsTest/GmapsPlaceDetailsOK.json")));
    }

    @Test
    public void createParentDirsOfFile() {
        assertDoesNotThrow(() ->FileUtil.createParentDirsOfFile(Path.of(
                "src/test/data/ApiStubsTest/GmapsPlaceDetailsOK.json")));
    }
}
