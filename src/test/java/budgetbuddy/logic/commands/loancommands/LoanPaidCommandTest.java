package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.person.Person;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.loanutil.LoanBuilder;
import budgetbuddy.testutil.loanutil.TypicalLoans;
import budgetbuddy.testutil.loanutil.TypicalPersons;

public class LoanPaidCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        model.getLoansManager().addLoan(TypicalLoans.JOHN_OUT_UNPAID);
        expectedModel.getLoansManager().addLoan(
                new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID).withStatus("PAID").build());
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoanPaidCommand(null, null));
        assertThrows(NullPointerException.class, () -> new LoanPaidCommand(null, new ArrayList<>()));
        assertThrows(NullPointerException.class, () -> new LoanPaidCommand(new ArrayList<>(), null));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_ITEM;
        LoanPaidCommand loanPaidCommand = new LoanPaidCommand(
                Collections.singletonList(targetIndex), new ArrayList<Person>());

        assertThrows(CommandException.class, () -> loanPaidCommand.execute(model));
    }

    @Test
    public void execute_validTargetIndex_paidSuccess() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        LoanPaidCommand loanPaidCommand = new LoanPaidCommand(
                Collections.singletonList(targetIndex), new ArrayList<Person>());

        String expectedMessage = String.format(
                LoanPaidCommand.MESSAGE_SUCCESS, String.format("%d", targetIndex.getOneBased()));
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.LOAN);

        assertCommandSuccess(loanPaidCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidTargetPerson_throwsCommandException() throws CommandException {
        Person targetPerson = TypicalPersons.ALICE;
        LoanPaidCommand loanPaidCommand = new LoanPaidCommand(
                new ArrayList<Index>(), Collections.singletonList(targetPerson));

        assertThrows(CommandException.class, () -> loanPaidCommand.execute(model));
    }

    @Test
    public void execute_validTargetPerson_paidSuccess() throws CommandException {
        Person targetPerson = TypicalLoans.JOHN_OUT_UNPAID.getPerson();
        LoanPaidCommand loanPaidCommand = new LoanPaidCommand(
                new ArrayList<Index>(), Collections.singletonList(targetPerson));

        String expectedMessage = String.format(
                LoanPaidCommand.MESSAGE_SUCCESS, String.format("%d", TypicalIndexes.INDEX_FIRST_ITEM.getOneBased()));
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.LOAN);

        assertCommandSuccess(loanPaidCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() throws CommandException {
        LoanPaidCommand firstLoanPaidCommand = new LoanPaidCommand(
                Collections.singletonList(TypicalIndexes.INDEX_FIRST_ITEM), new ArrayList<Person>());
        LoanPaidCommand secondLoanPaidCommand = new LoanPaidCommand(
                Collections.singletonList(TypicalIndexes.INDEX_SECOND_ITEM), new ArrayList<Person>());

        // same object -> returns true
        assertEquals(firstLoanPaidCommand, firstLoanPaidCommand);

        // same values -> returns true
        LoanPaidCommand firstLoanPaidCommandCopy = new LoanPaidCommand(
                Collections.singletonList(TypicalIndexes.INDEX_FIRST_ITEM), new ArrayList<Person>());
        assertEquals(firstLoanPaidCommand, firstLoanPaidCommandCopy);

        // different types -> returns false
        assertNotEquals(firstLoanPaidCommand, 5);

        // null -> returns false
        assertNotEquals(firstLoanPaidCommand, null);

        // different targets -> returns false
        assertNotEquals(firstLoanPaidCommand, secondLoanPaidCommand);
    }
}
