package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;

//@@author waifonglee
public class ListTagCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboard(), new UserPrefs());
        expectedModel = new ModelManager(model.getBillboard(), new UserPrefs());
    }

    @Test
    public void execute_listTags_success() {
        String expectedFeedback = "Here are the existing tags(s):\n[bills],\n[friends],\n[leisure],\n[monday]";
        CommandResult expectedCommandResult = new CommandResult(expectedFeedback);
        assertCommandSuccess(new ListTagCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noTags_success() {
        String expectedFeedback = "There is no existing tag";
        model = new ModelManager(new Billboard(), new UserPrefs());
        expectedModel = new ModelManager(model.getBillboard(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(expectedFeedback);
        assertCommandSuccess(new ListTagCommand(), model, expectedCommandResult, expectedModel);
    }
}
