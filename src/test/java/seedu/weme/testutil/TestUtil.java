package seedu.weme.testutil;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import seedu.weme.commons.core.index.Index;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final Path SANDBOX_FOLDER_2 = Paths.get("src", "test", "data", "sandbox2");

    private static final Path SANDBOX_FOLDER_3 = Paths.get("src", "test", "data", "sandbox3");

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
     * Returns the sandbox folder path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getSandboxFolder() {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER;
    }

    public static Path getSecondSandboxFolder() {
        try {
            Files.createDirectories(SANDBOX_FOLDER_2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER_2;
    }

    public static Path getInvalidSandboxFolder() {
        return SANDBOX_FOLDER_3;
    }

    /**
     * Clears all files and directories in the sandbox folder.
     */
    public static void clearSandBoxFolder() {
        if (!Files.exists(SANDBOX_FOLDER)) {
            return;
        }

        try {
            Files.walk(SANDBOX_FOLDER)
                .map(Path::toFile)
                .sorted(Comparator.reverseOrder())
                .forEach(File::delete);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Returns the middle index of the meme in the {@code model}'s meme list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredMemeList().size() / 2);
    }

    /**
     * Returns the last index of the meme in the {@code model}'s meme list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredMemeList().size());
    }

    /**
     * Returns the meme in the {@code model}'s meme list at {@code index}.
     */
    public static Meme getMeme(Model model, Index index) {
        return model.getFilteredMemeList().get(index.getZeroBased());
    }
}
