package seedu.weme.logic.commands.importcommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.model.DirectoryPath;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

class LoadCommandTest extends ApplicationTest {

    private static final DirectoryPath LOAD_DIRECTORY_PATH = new DirectoryPath("src/test/data/memes/");

    private final Model model = new ModelManager(getTypicalWeme(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());

    @Test
    public void execute_load_successMessage() throws IOException {

        List<Path> pathList = FileUtil.loadImagePath(LOAD_DIRECTORY_PATH);
        expectedModel.loadMemes(pathList);

        LoadCommand loadCommand = new LoadCommand(LOAD_DIRECTORY_PATH);

        assertCommandSuccess(loadCommand, model, LoadCommand.MESSAGE_SUCCESS, expectedModel);

    }

}
