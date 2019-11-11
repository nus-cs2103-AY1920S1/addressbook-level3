package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.logic.commands.DeleteCommand.MESSAGE_DELETE_EXPENSE_SUCCESS;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.getDeleteTypicalBillboard;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.versionedbillboard.VersionedBillboard;

class UndoCommandTest {

    @Test
    void execute() {
        Model model = new ModelManager(getTypicalBillboard(), new UserPrefs());
        Model deleteModel = new ModelManager(getDeleteTypicalBillboard(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getBillboard(), new UserPrefs());
        VersionedBillboard.clearStateList();
        VersionedBillboard.commit(model);
        UndoCommand undo = new UndoCommand();
        assertCommandFailure(undo, model, Messages.MESSAGE_NOT_UNDOABLE);
        VersionedBillboard.commit(deleteModel);
        String deleteCmd = "delete 1";
        CommandResult deleteCommandResult = new CommandResult(String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, BILLS),
                false, false, CommandResult.DEFAULT_LIST_VIEW);
        VersionedBillboard.addCmd(deleteCmd, deleteCommandResult);
        String expectedMessage = String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, deleteCmd);
        assertCommandSuccess(undo, deleteModel, expectedMessage, expectedModel);
    }

}
