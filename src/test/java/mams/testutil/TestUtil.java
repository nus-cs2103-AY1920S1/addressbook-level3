package mams.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import mams.commons.core.index.Index;
import mams.model.Model;
import mams.model.student.Student;

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
     * Returns the middle index of the student in the {@code model}'s student list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredStudentList().size() / 2);
    }

    /**
     * Returns the last index of the student in the {@code model}'s student list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredStudentList().size());
    }

    /**
     * Returns the student in the {@code model}'s student list at {@code index}.
     */
    public static Student getStudent(Model model, Index index) {
        return model.getFilteredStudentList().get(index.getZeroBased());
    }
}
