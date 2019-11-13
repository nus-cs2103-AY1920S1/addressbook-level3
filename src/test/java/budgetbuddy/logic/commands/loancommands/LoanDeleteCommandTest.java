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
import budgetbuddy.testutil.loanutil.TypicalLoans;
import budgetbuddy.testutil.loanutil.TypicalPersons;

public class LoanDeleteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        model.getLoansManager().addLoan(TypicalLoans.JOHN_OUT_UNPAID);
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoanDeleteCommand(null, null));
        assertThrows(NullPointerException.class, () -> new LoanDeleteCommand(null, new ArrayList<>()));
        assertThrows(NullPointerException.class, () -> new LoanDeleteCommand(new ArrayList<>(), null));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_ITEM;
        LoanDeleteCommand loanDeleteCommand = new LoanDeleteCommand(
                Collections.singletonList(targetIndex), new ArrayList<Person>());

        assertThrows(CommandException.class, () -> loanDeleteCommand.execute(model));
    }

    @Test
    public void execute_validTargetIndex_deleteSuccess() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        LoanDeleteCommand loanDeleteCommand = new LoanDeleteCommand(
                Collections.singletonList(targetIndex), new ArrayList<Person>());

        String expectedMessage = String.format(
                LoanDeleteCommand.MESSAGE_SUCCESS, String.format("%d", targetIndex.getOneBased()));
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.LOAN);

        assertCommandSuccess(loanDeleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidTargetPerson_throwsCommandException() throws CommandException {
        Person targetPerson = TypicalPersons.ALICE;
        LoanDeleteCommand loanDeleteCommand = new LoanDeleteCommand(
                new ArrayList<Index>(), Collections.singletonList(targetPerson));

        assertThrows(CommandException.class, () -> loanDeleteCommand.execute(model));
    }

    @Test
    public void execute_validTargetPerson_deleteSuccess() throws CommandException {
        Person targetPerson = TypicalLoans.JOHN_OUT_UNPAID.getPerson();
        LoanDeleteCommand loanDeleteCommand = new LoanDeleteCommand(
                new ArrayList<Index>(), Collections.singletonList(targetPerson));

        String expectedMessage = String.format(
                LoanDeleteCommand.MESSAGE_SUCCESS, String.format("%d", TypicalIndexes.INDEX_FIRST_ITEM.getOneBased()));
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.LOAN);

        assertCommandSuccess(loanDeleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() throws CommandException {
        LoanDeleteCommand firstLoanDeleteCommand = new LoanDeleteCommand(
                Collections.singletonList(TypicalIndexes.INDEX_FIRST_ITEM), new ArrayList<Person>());
        LoanDeleteCommand secondLoanDeleteCommand = new LoanDeleteCommand(
                Collections.singletonList(TypicalIndexes.INDEX_SECOND_ITEM), new ArrayList<Person>());

        // same object -> returns true
        assertEquals(firstLoanDeleteCommand, firstLoanDeleteCommand);

        // same values -> returns true
        LoanDeleteCommand firstLoanDeleteCommandCopy = new LoanDeleteCommand(
                Collections.singletonList(TypicalIndexes.INDEX_FIRST_ITEM), new ArrayList<Person>());
        assertEquals(firstLoanDeleteCommand, firstLoanDeleteCommandCopy);

        // different types -> returns false
        assertNotEquals(firstLoanDeleteCommand, 5);

        // null -> returns false
        assertNotEquals(firstLoanDeleteCommand, null);

        // different targets -> returns false
        assertNotEquals(firstLoanDeleteCommand, secondLoanDeleteCommand);
    }
}
