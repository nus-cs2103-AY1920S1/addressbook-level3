package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");
    private static final Path CSV_UTIL_TEST_FOLDER = Paths.get("src", "test", "data", "CsvUtilTest");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Appends {@code fileName} to the CsvUtilTest folder path and returns the resulting path.
     * Creates the CsvUtilTest folder if it doesn't exist.
     */
    public static Path getFilePathInCsvUtilTestFolder(String fileName) {
        try {
            Files.createDirectories(CSV_UTIL_TEST_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CSV_UTIL_TEST_FOLDER.resolve(fileName);
    }
}
