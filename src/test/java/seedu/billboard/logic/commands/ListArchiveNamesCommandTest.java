package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboardWithArchiveExpenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;

public class ListArchiveNamesCommandTest {
    private Model withArchivesModel;
    private Model noArchivesModel;
    private Model withArchivesExpectedModel;
    private Model noArchivesExpectedModel;

    @BeforeEach
    public void setUp() {
        withArchivesModel = new ModelManager(getTypicalBillboardWithArchiveExpenses(), new UserPrefs());
        noArchivesModel = new ModelManager(getTypicalBillboard(), new UserPrefs());
        withArchivesExpectedModel = new ModelManager(withArchivesModel.getCombinedBillboard(), new UserPrefs());
        noArchivesExpectedModel = new ModelManager(noArchivesModel.getCombinedBillboard(), new UserPrefs());
    }

    @Test
    public void execute_listArchiveNames_success() {
        String expectedFeedback = "Here are the existing archive(s):\n[hobbies],\n[luxury]";
        CommandResult expectedCommandResult = new CommandResult(expectedFeedback);
        assertCommandSuccess(new ListArchiveNamesCommand(),
                withArchivesModel, expectedCommandResult, withArchivesExpectedModel);
    }

    @Test
    public void execute_noArchivesToList_success() {
        String expectedFeedback = "There are no existing archives";
        CommandResult expectedCommandResult = new CommandResult(expectedFeedback);
        assertCommandSuccess(new ListArchiveNamesCommand(),
                noArchivesModel, expectedCommandResult, noArchivesExpectedModel);
    }

}
