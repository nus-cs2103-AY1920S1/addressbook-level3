package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandFailure;
import static mams.logic.commands.ApproveCommand.MESSAGE_NOT_IMPLEMENTED_YET;
//import static mams.testutil.TypicalAppeals.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class ApproveCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute() {
        assertCommandFailure(new ApproveCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
