package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ShowNotificationsCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

//@@author shaoyi1997
class ShowNotificationsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_notifButtonNotInstantiated_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> new ShowNotificationsCommand().execute(model));
    }

    @Test
    public void execute_showNotif_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS);
        assertCommandSuccess(new ShowNotificationsCommand(), model, expectedCommandResult, expectedModel);
    }
}
//@@author
