package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getDeleteTypicalBillboard;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.versionedbillboard.VersionedBillboard;

class RedoCommandTest {
    private Model model;
    private RedoCommand redo;

    @BeforeEach
    void setUp() {
        model = new ModelManager(getTypicalBillboard(), new UserPrefs());
        VersionedBillboard.clearList();
        VersionedBillboard.commit(model);
        redo = new RedoCommand();
    }

    @Test
    void execute() {
        assertCommandFailure(redo, model, Messages.MESSAGE_NOT_REDOABLE);
        Model deleteModel = new ModelManager(getDeleteTypicalBillboard(), new UserPrefs());
        VersionedBillboard.commit(deleteModel);
        String deleteCmd = "delete 1";
        VersionedBillboard.addCmd(deleteCmd);
        VersionedBillboard.undo();

        String expectedMessage = String.format(RedoCommand.MESSAGE_REDO_SUCCESS, deleteCmd);
        assertCommandSuccess(redo, model, expectedMessage, deleteModel);
    }
}
