package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanEditCommand.LoanEditDescriptor;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.loanutil.LoanBuilder;
import budgetbuddy.testutil.loanutil.LoanEditDescriptorBuilder;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class LoanEditCommandTest {

    private Model model = new ModelManager();

    @BeforeEach
    public void initialize() {
        model.getLoansManager().addLoan(TypicalLoans.JOHN_OUT_UNPAID);
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new LoanEditCommand(null, null));
        assertThrows(NullPointerException.class, () ->
                new LoanEditCommand(null, new LoanEditDescriptor()));
        assertThrows(NullPointerException.class, () ->
                new LoanEditCommand(TypicalIndexes.INDEX_FIRST_ITEM, null));
    }

    @Test
    public void execute_allFieldsSpecified_editSuccess() {
        Loan editedLoan = new LoanBuilder().build();
        LoanEditDescriptor loanEditDescriptor = new LoanEditDescriptorBuilder(editedLoan).build();
        LoanEditCommand loanEditCommand = new LoanEditCommand(TypicalIndexes.INDEX_FIRST_ITEM, loanEditDescriptor);

        String expectedMessage = String.format(
                LoanEditCommand.MESSAGE_SUCCESS, TypicalIndexes.INDEX_FIRST_ITEM.getOneBased());
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.LOAN);
        Model expectedModel = new ModelManager();
        expectedModel.getLoansManager().addLoan(editedLoan);

        assertCommandSuccess(loanEditCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_editSuccess() {
        Loan editedLoan = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID).withDescription("New Description").build();
        LoanEditDescriptor loanEditDescriptor = new LoanEditDescriptorBuilder(editedLoan).build();
        LoanEditCommand loanEditCommand = new LoanEditCommand(TypicalIndexes.INDEX_FIRST_ITEM, loanEditDescriptor);

        String expectedMessage = String.format(
                LoanEditCommand.MESSAGE_SUCCESS, TypicalIndexes.INDEX_FIRST_ITEM.getOneBased());
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.LOAN);
        Model expectedModel = new ModelManager();
        expectedModel.getLoansManager().addLoan(new LoanBuilder(editedLoan).build());

        assertCommandSuccess(loanEditCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldsSpecified_throwsCommandException() {
        LoanEditDescriptor loanEditDescriptor = new LoanEditDescriptorBuilder().build();
        LoanEditCommand loanEditCommand = new LoanEditCommand(TypicalIndexes.INDEX_FIRST_ITEM, loanEditDescriptor);

        assertThrows(CommandException.class, () -> loanEditCommand.execute(model));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() {
        Loan editedLoan = new LoanBuilder().build();
        LoanEditDescriptor loanEditDescriptor = new LoanEditDescriptorBuilder(editedLoan).build();
        LoanEditCommand loanEditCommand = new LoanEditCommand(TypicalIndexes.INDEX_SECOND_ITEM, loanEditDescriptor);

        assertThrows(CommandException.class, () -> loanEditCommand.execute(model));
    }

    @Test
    public void equals() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        LoanEditDescriptor loanEditDescriptor = new LoanEditDescriptorBuilder(TypicalLoans.JOHN_OUT_UNPAID).build();
        LoanEditCommand loanEditCommand = new LoanEditCommand(targetIndex, loanEditDescriptor);

        // same object -> returns true
        assertEquals(loanEditCommand, loanEditCommand);

        // same values -> returns true
        LoanEditCommand loanEditCommandCopy = new LoanEditCommand(targetIndex, loanEditDescriptor);
        assertEquals(loanEditCommand, loanEditCommandCopy);

        // different types -> returns false
        assertNotEquals(loanEditCommand, 5);

        // null -> returns false
        assertNotEquals(loanEditCommand, null);

        // different index -> returns false
        LoanEditCommand loanEditCommandWithDifferentIndex =
                new LoanEditCommand(TypicalIndexes.INDEX_SECOND_ITEM, loanEditDescriptor);
        assertNotEquals(loanEditCommand, loanEditCommandWithDifferentIndex);

        // different descriptor -> returns false
        LoanEditCommand loanEditCommandWithDifferentDescriptor =
                new LoanEditCommand(targetIndex, new LoanEditDescriptorBuilder(TypicalLoans.MARY_IN_UNPAID).build());
        assertNotEquals(loanEditCommand, loanEditCommandWithDifferentDescriptor);
    }
}
