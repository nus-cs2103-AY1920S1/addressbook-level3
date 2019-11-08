package seedu.ichifund.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.model.Model;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

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
     * Returns the middle index of the transaction in the {@code model}'s person list.
     */
    public static Index getMidTransactionIndex(Model model) {
        return Index.fromOneBased(model.getFilteredTransactionList().size() / 2);
    }

    /**
     * Returns the last index of the transaction in the {@code model}'s person list.
     */
    public static Index getLastTransactionIndex(Model model) {
        return Index.fromOneBased(model.getFilteredTransactionList().size());
    }
}
