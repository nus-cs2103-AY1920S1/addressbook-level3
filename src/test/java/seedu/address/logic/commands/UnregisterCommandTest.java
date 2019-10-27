package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BORROWER_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BORROWER_ID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.testutil.BorrowerBuilder;

public class UnregisterCommandTest {
    @Test
    public void constructor_nullBorrowerId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnregisterCommand(null));
    }

    @Test
    public void execute_borrowerAcceptedByModel_unregisterSuccessful() throws Exception {
        Model modelManager = new ModelManager();
        Borrower validBorrower = new BorrowerBuilder().actualBuild();
        // register the borrower
        new RegisterCommand(validBorrower).execute(modelManager);

        //unregister the borrower
        CommandResult commandResult = new UnregisterCommand(validBorrower.getBorrowerId()).execute(modelManager);
        assertEquals(String.format(UnregisterCommand.MESSAGE_SUCCESS, validBorrower),
                commandResult.getFeedbackToUser());
        assertEquals(new ArrayList<Borrower>(), modelManager.getBorrowerRecords().getBorrowerList());
    }

    @Test void execute_borrowerIdNotFound_unregisterFailure() throws Exception {
        Model modelManager = new ModelManager();
        Command command = new UnregisterCommand(new BorrowerId(VALID_BORROWER_ID));

        assertCommandFailure(command, modelManager, MESSAGE_NO_SUCH_BORROWER_ID);
    }
}
