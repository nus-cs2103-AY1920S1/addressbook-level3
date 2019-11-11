package seedu.eatme.logic.commands;

import static seedu.eatme.logic.commands.CommandTestUtil.VALID_FILE_NO_PREFIX_ALICE;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_FILE_NO_PREFIX_JOHN;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;

public class LoadCommandTest {
    private static final Path FILE_PATH =
            Paths.get("src", "test", "data", "LoadCommandTest", VALID_FILE_NO_PREFIX_JOHN);

    private Model model;

    @BeforeEach
    public void setUp() {
        UserPrefs userPrefsTest = new UserPrefs();
        userPrefsTest.setEateryListFilePath(FILE_PATH);
        model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), userPrefsTest);
    }

    @Test
    public void execute_duplicateFileLoad_throwsParseException() {
        LoadCommand command = new LoadCommand(FILE_PATH);

        assertCommandFailure(command, model,
                String.format(LoadCommand.MESSAGE_ALREADY_LOADED, model.getEateryListFilePath()));
    }

    @Test
    public void execute_fileLoadNotFound_throwsParseException() {
        Path nonExistentFile = Paths.get("src", "test", "data", "LoadCommandTest", "nonExistent.json");
        LoadCommand command = new LoadCommand(nonExistentFile);

        assertCommandFailure(command, model,
                String.format(LoadCommand.MESSAGE_PROFILE_NOT_FOUND, nonExistentFile));
    }

    @Test
    public void execute_fileLoad_success() {
        Path newPath = Paths.get("src", "test", "data", "LoadCommandTest", VALID_FILE_NO_PREFIX_ALICE);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setEateryListFilePath(newPath);
        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), userPrefs);

        LoadCommand command = new LoadCommand(newPath);

        assertCommandSuccess(command, model,
                String.format(LoadCommand.MESSAGE_SUCCESS_LOADED, newPath), expectedModel);
    }
}
