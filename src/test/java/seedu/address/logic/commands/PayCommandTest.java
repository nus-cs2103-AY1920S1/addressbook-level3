package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_OUTSTANDING_FINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CENT_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBorrowers.IDA;
import static seedu.address.testutil.TypicalBorrowers.JANNA;
import static seedu.address.testutil.TypicalLoans.LOAN_8;
import static seedu.address.testutil.TypicalLoans.LOAN_9;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.FineUtil;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class PayCommandTest {

    @Test
    public void execute_outstandingFines_paymentSuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(JANNA);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_8);
        loanRecords.addLoan(LOAN_9);

        Model actualModel = new ModelManager(new Catalog(), loanRecords, borrowerRecords, new UserPrefs());
        actualModel.setServingBorrower(JANNA);
        Model expectedModel = new ModelManager(new Catalog(), new LoanRecords(loanRecords),
                new BorrowerRecords(borrowerRecords), new UserPrefs());
        expectedModel.setServingBorrower(JANNA);

        PayCommand payCommand = new PayCommand(VALID_CENT_AMOUNT);

        int change = expectedModel.payFines(VALID_CENT_AMOUNT);
        String amountPaidInDollars = FineUtil.centsToDollarString(VALID_CENT_AMOUNT - change);
        String outstandingFineInDollars = FineUtil.centsToDollarString(
                expectedModel.getServingBorrower().getOutstandingFineAmount());
        String changeInDollars = FineUtil.centsToDollarString(change);
        String expectedMessage = String.format(PayCommand.MESSAGE_SUCCESS, amountPaidInDollars, JANNA,
                outstandingFineInDollars, changeInDollars);

        assertCommandSuccess(payCommand, actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notInServeMode_paymentUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(JANNA);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_8);
        loanRecords.addLoan(LOAN_9);

        Model model = new ModelManager(new Catalog(), loanRecords, borrowerRecords, new UserPrefs());

        PayCommand payCommand = new PayCommand(VALID_CENT_AMOUNT);
        String expectedMessage = MESSAGE_NOT_IN_SERVE_MODE;

        assertCommandFailure(payCommand, model, expectedMessage);
    }

    @Test
    public void execute_noOutstandingFines_paymentUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);

        Model model = new ModelManager(new Catalog(), new LoanRecords(), borrowerRecords, new UserPrefs());
        model.setServingBorrower(IDA);

        PayCommand payCommand = new PayCommand(VALID_CENT_AMOUNT);
        String expectedMessage = MESSAGE_NO_OUTSTANDING_FINE;

        assertCommandFailure(payCommand, model, expectedMessage);

    }

    @Test
    public void equals() {
        PayCommand payCommand1 = new PayCommand(VALID_CENT_AMOUNT);
        PayCommand payCommand2 = new PayCommand(VALID_CENT_AMOUNT);
        PayCommand payCommand3 = new PayCommand(VALID_CENT_AMOUNT + 10);

        // same object -> returns true
        assertTrue(payCommand1.equals(payCommand1));

        // same values -> returns true
        assertTrue(payCommand1.equals(payCommand2));

        // different values -> returns false
        assertFalse(payCommand1.equals(payCommand3));

        // null -> returns false
        assertFalse(payCommand1.equals(null));

        // different type -> returns false
        assertFalse(payCommand1.equals(1));
    }
}
