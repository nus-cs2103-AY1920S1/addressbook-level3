package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.testutil.loanutil.LoanBuilder;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class LoanCommandTest {

    private Model model;

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
    }

    @Test
    public void constructor_nullLoan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoanCommand(null));
    }

    @Test
    public void execute_validLoan_addSuccessful() throws CommandException {
        Loan validLoan = new LoanBuilder().build();
        CommandResult commandResult = new LoanCommand(validLoan).execute(model);

        assertEquals(String.format(LoanCommand.MESSAGE_SUCCESS, validLoan), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validLoan), model.getLoansManager().getLoans());
    }

    @Test
    public void execute_duplicateLoan_throwsCommandException() {
        Loan loan = new LoanBuilder().build();
        Loan loanCopy = new LoanBuilder().build();
        model.getLoansManager().addLoan(loan);

        assertThrows(CommandException.class, () -> new LoanCommand(loanCopy).execute(model));
    }

    @Test
    public void equals() {
        Loan johnOutUnpaid = TypicalLoans.JOHN_OUT_UNPAID;
        Loan zedInPaid = TypicalLoans.ZED_IN_PAID;
        LoanCommand johnLoanCommand = new LoanCommand(johnOutUnpaid);
        LoanCommand zedLoanCommand = new LoanCommand(zedInPaid);

        // same object -> returns true
        assertEquals(johnLoanCommand, johnLoanCommand);

        // same values -> returns true
        LoanCommand johnLoanCommandCopy = new LoanCommand(johnOutUnpaid);
        assertEquals(johnLoanCommand, johnLoanCommandCopy);

        // different types -> returns false
        assertNotEquals(johnLoanCommand, 1);

        // null -> returns false
        assertNotEquals(johnLoanCommand, null);

        // different loan -> returns false
        assertNotEquals(johnLoanCommand, zedLoanCommand);
    }
}
