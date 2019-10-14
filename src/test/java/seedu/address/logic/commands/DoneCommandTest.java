package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.testutil.TypicalBorrowers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.testutil.Assert;

public class DoneCommandTest {

    @Test
    public void execute_exitsServeMode_successful() throws Exception {
        Model modelManager = new ModelManager();
        //registers BOB
        new RegisterCommand(BOB).execute(modelManager);
        //serves BOB
        new ServeCommand(new BorrowerId(VALID_ID_BOB)).execute(modelManager);

        assertNotNull(modelManager.getServingBorrower());
        //done
        DoneCommand doneCommand = new DoneCommand();
        doneCommand.execute(modelManager);

        Assert.assertThrows(CommandException.class, MESSAGE_NOT_IN_SERVE_MODE, () ->
                doneCommand.execute(modelManager));
    }

    @Test
    public void equal() {
        DoneCommand standardCommand = new DoneCommand();

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));
    }
}
