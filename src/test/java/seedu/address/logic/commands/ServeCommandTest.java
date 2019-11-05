package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BORROWER_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.testutil.TypicalBorrowers.ALICE;
import static seedu.address.testutil.TypicalBorrowers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.testutil.Assert;

public class ServeCommandTest {

    @Test
    public void constructor_nullBorrowerId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ServeCommand(null));
    }

    @Test
    public void execute_borrowerIdAcceptedByModel_addSuccessful() throws Exception {
        Model modelManager = new ModelManager();
        new RegisterCommand(BOB).execute(modelManager);

        BorrowerId validBorrowerId = new BorrowerId(VALID_ID_BOB);
        CommandResult commandResult = new ServeCommand(validBorrowerId).execute(modelManager);

        assertEquals(String.format(ServeCommand.MESSAGE_SUCCESS, BOB),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_borrowerIdNotFound_throwsCommandException() {
        Model modelManager = new ModelManager();
        BorrowerId invalidId = new BorrowerId(VALID_ID_AMY);
        ServeCommand serveCommand = new ServeCommand(invalidId);

        Assert.assertThrows(CommandException.class, MESSAGE_NO_SUCH_BORROWER_ID, () ->
                serveCommand.execute(modelManager));
    }

    @Test
    public void execute_alreadyInServeMode_throwsCommandException() throws CommandException {
        Model modelManager = new ModelManager();
        new RegisterCommand(BOB).execute(modelManager);
        new RegisterCommand(ALICE).execute(modelManager);

        new ServeCommand(ALICE.getBorrowerId()).execute(modelManager);
        ServeCommand serveCommand = new ServeCommand(BOB.getBorrowerId());

        String msg = String.format(ServeCommand.MESSAGE_ALREADY_IN_SERVE_MODE, ALICE.getName());
        Assert.assertThrows(CommandException.class, msg, () -> serveCommand.execute(modelManager));
    }
}
