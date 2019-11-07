package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_UNREGISTER_WHILE_SERVING;
import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_UNREGISTER_WITH_BOOKS_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BORROWER_ID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.BorrowerBuilder.DEFAULT_NAME;
import static seedu.address.testutil.TypicalBorrowers.ALICE;
import static seedu.address.testutil.TypicalBorrowers.ID_FIRST_BORROWER;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;
import static seedu.address.testutil.TypicalLoans.LOAN_2;

import org.junit.jupiter.api.Test;

import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.testutil.BorrowerBuilder;

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
        expectedModel.unregisterBorrower(new BorrowerBuilder(ALICE).build());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSerialNumber_throwsCommandException() {
        BorrowerId outOfBoundBorrowerId = new BorrowerId("K9999");
        UnregisterCommand command = new UnregisterCommand(outOfBoundBorrowerId);

        assertCommandFailure(command, model, MESSAGE_NO_SUCH_BORROWER_ID);
    }

    @Test
    public void execute_unregisterServingBorrower_throwsCommandException() {
        BorrowerId currentlyServing = new BorrowerId("K0001");
        UnregisterCommand command = new UnregisterCommand(currentlyServing);
        model.setServingBorrower(ALICE);

        assertCommandFailure(
                command, model, String.format(MESSAGE_CANNOT_UNREGISTER_WHILE_SERVING, ALICE.getName().toString()));
    }

    @Test
    public void execute_unregisterBorrowerWithLoan_throwsCommandException() {
        BorrowerId withLoan = new BorrowerId("K0002");
        UnregisterCommand command = new UnregisterCommand(withLoan);
        Borrower borrower = new BorrowerBuilder().withCurrentLoan(LOAN_2).withBorrowerId("K0002").build();
        Model testModel = new ModelManager(new Catalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
        testModel.registerBorrower(borrower);

        assertCommandFailure(
                command, testModel, String.format(
                        MESSAGE_CANNOT_UNREGISTER_WITH_BOOKS_ON_LOAN, DEFAULT_NAME));
    }

}
