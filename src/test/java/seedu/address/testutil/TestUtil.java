package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.diary.components.Diary;
import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.recipe.components.Recipe;

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
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredExerciseList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredExerciseList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Exercise getPerson(Model model, Index index) {
        return model.getFilteredExerciseList().get(index.getZeroBased());
    }

    /**
     * Returns the recipe in the {@code model}'s recipe list at {@code index}.
     */
    public static Recipe getRecipe(Model model, Index index) {
        return model.getFilteredRecipeList().get(index.getZeroBased());
    }

    /**
     * Returns the diary in the {@code model}'s diary list at {@code index}.
     */
    public static Diary getDiary(Model model, Index index) {
        return model.getFilteredDiaryList().get(index.getZeroBased());
    }
}
