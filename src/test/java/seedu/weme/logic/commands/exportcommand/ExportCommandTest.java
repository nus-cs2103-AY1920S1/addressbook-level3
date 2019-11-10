package seedu.weme.logic.commands.exportcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.memecommand.MemeClearCommand;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.DirectoryPath;
import seedu.weme.testutil.TestUtil;

class ExportCommandTest extends ApplicationTest {

    private static final String VALID_SANDBOX_DIRECTORY = TestUtil.getSandboxFolder().toString();
    private static final String VALID_SANDBOX_DIRECTORY_2 = TestUtil.getSecondSandboxFolder().toString();

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute_export_successMessage() {
        Meme memeToStage = model.getWeme().getMemeList().get(INDEX_FIRST.getZeroBased());
        model.stageMeme(memeToStage);
        expectedModel.stageMeme(memeToStage);

        ExportCommand exportCommand = new ExportCommand(new DirectoryPath(VALID_SANDBOX_DIRECTORY));

        assertCommandSuccess(exportCommand, model, ExportCommand.MESSAGE_SUCCESS, expectedModel);

        TestUtil.clearSandBoxFolder();
    }

    @Test
    public void equals() {
        final ExportCommand standardCommand = new ExportCommand(new DirectoryPath(VALID_SANDBOX_DIRECTORY));

        // same values -> returns true
        ExportCommand commandWithSameValues = new ExportCommand(new DirectoryPath(VALID_SANDBOX_DIRECTORY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new MemeClearCommand()));

        // different paths -> returns false
        assertFalse(standardCommand.equals(new ExportCommand(new DirectoryPath(VALID_SANDBOX_DIRECTORY_2))));
    }

}
