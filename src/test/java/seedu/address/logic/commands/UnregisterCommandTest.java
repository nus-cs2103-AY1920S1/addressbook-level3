package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BORROWER_ID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBorrowers.ALICE;
import static seedu.address.testutil.TypicalBorrowers.ID_FIRST_BORROWER;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;

import org.junit.jupiter.api.Test;

import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.borrower.BorrowerId;

public class UnregisterCommandTest {

    private Model model = new ModelManager(
            new Catalog(), new LoanRecords(), getTypicalBorrowerRecords(), new UserPrefs());

    @Test
    public void constructor_nullBorrowerId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnregisterCommand(null));
    }

    @Test
    public void execute_validSerialNumber_success() {
        UnregisterCommand command = new UnregisterCommand(ID_FIRST_BORROWER);

        String expectedMessage = String.format(UnregisterCommand.MESSAGE_SUCCESS, ALICE);

        ModelManager expectedModel = new ModelManager(
                model.getCatalog(), model.getLoanRecords(), model.getBorrowerRecords(), new UserPrefs());
        expectedModel.unregisterBorrower(ALICE);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSerialNumber_throwsCommandException() {
        BorrowerId outOfBoundBorrowerId = new BorrowerId("K9999");
        UnregisterCommand command = new UnregisterCommand(outOfBoundBorrowerId);

        assertCommandFailure(command, model, MESSAGE_NO_SUCH_BORROWER_ID);
    }


}
