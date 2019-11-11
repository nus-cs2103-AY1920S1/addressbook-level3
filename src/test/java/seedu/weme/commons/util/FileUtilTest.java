package seedu.weme.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.testutil.TestUtil;

public class FileUtilTest extends ApplicationTest {

    private static final String VALID_CHARMANDER_PATH_STRING = "src/test/data/memes/charmander_meme.jpg";
    private static final String INVALID_CHARMANDER_PATH_STRING = "src/test/data/memes/invalid/charmander_meme.jpg";
    private static final String VALID_DIRECTORY = TestUtil.getSandboxFolder().toString();

    private static final String CHARMANDER_FILENAME = "charmander_meme.jpg";

    @Test
    public void execute_isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void execute_isValidImageFile() throws IOException {
        assertEquals(true, FileUtil.isValidImageFile(Paths.get(VALID_CHARMANDER_PATH_STRING)));

        assertEquals(false, FileUtil.isValidImageFile(Paths.get(INVALID_CHARMANDER_PATH_STRING)));
    }


    @Test
    public void execute_getValidExtension() {
        Optional<String> optionalExtension = FileUtil
                .getExtension(Paths.get("charmander_meme.jpg"));
        assertEquals(optionalExtension.get(), "jpg");
    }

    @Test
    public void execute_getEmptyExtension() {
        Optional<String> optionalExtension = FileUtil
                .getExtension(Paths.get("charmander_meme"));
        assertEquals(optionalExtension, Optional.empty());
    }

    @Test
    public void execute_isFileExists() {
        assertEquals(true, FileUtil.isFileExists(Paths.get(VALID_CHARMANDER_PATH_STRING)));

        assertEquals(false, FileUtil.isFileExists(Paths.get(INVALID_CHARMANDER_PATH_STRING)));
    }

    @Test
    public void execute_buildFilePath() {
        Path validDirectoryPath = Paths.get(VALID_DIRECTORY);
        String newFileName = "new_charmander_meme";
        String actualPath = FileUtil
                .buildFilePath(validDirectoryPath, newFileName, Paths.get(VALID_CHARMANDER_PATH_STRING));
        String expectedNewPath = VALID_DIRECTORY
                + "/"
                + newFileName
                + "."
                + FileUtil.getExtension(Paths.get(VALID_CHARMANDER_PATH_STRING)).get();

        assertEquals(expectedNewPath, actualPath);
    }

    @Test
    public void execute_getFileName() {
        String fileName = FileUtil.getFileName(VALID_CHARMANDER_PATH_STRING);
        assertEquals(fileName, CHARMANDER_FILENAME);
    }

    @Test
    public void execute_copy() throws IOException {
        Path invalidPath = Paths.get(INVALID_CHARMANDER_PATH_STRING);
        Path validPath = Paths.get(VALID_CHARMANDER_PATH_STRING);
        Path validDirectoryPath = Paths.get(VALID_DIRECTORY);

        TestUtil.clearSandBoxFolder();
        String newPath = FileUtil.buildFilePath(validDirectoryPath,
                FileUtil.getFileName("charmander_meme_clone"), validPath);
        FileUtil.copy(validPath, Paths.get(newPath));

        assertEquals(true, FileUtil.isFileExists(Paths.get(newPath)));

        assertThrows(IOException.class, () -> FileUtil.copy(invalidPath, Paths.get(newPath)));

        TestUtil.clearSandBoxFolder();

    }

}
