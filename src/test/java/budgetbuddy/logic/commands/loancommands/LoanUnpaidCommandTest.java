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

public class LoanUnpaidCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        model.getLoansManager().addLoan(TypicalLoans.ZED_IN_PAID);
        expectedModel.getLoansManager().addLoan(
                new LoanBuilder(TypicalLoans.ZED_IN_PAID).withStatus("UNPAID").build());
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoanUnpaidCommand(null, null));
        assertThrows(NullPointerException.class, () -> new LoanUnpaidCommand(null, new ArrayList<>()));
        assertThrows(NullPointerException.class, () -> new LoanUnpaidCommand(new ArrayList<>(), null));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_ITEM;
        LoanUnpaidCommand loanUnpaidCommand = new LoanUnpaidCommand(
                Collections.singletonList(targetIndex), new ArrayList<Person>());

        assertThrows(CommandException.class, () -> loanUnpaidCommand.execute(model));
    }

    @Test
    public void execute_validTargetIndex_unpaidSuccess() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        LoanUnpaidCommand loanUnpaidCommand = new LoanUnpaidCommand(
                Collections.singletonList(targetIndex), new ArrayList<Person>());

        String expectedMessage = String.format(
                LoanUnpaidCommand.MESSAGE_SUCCESS, String.format("%d", targetIndex.getOneBased()));
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.LOAN);

        assertCommandSuccess(loanUnpaidCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidTargetPerson_throwsCommandException() throws CommandException {
        Person targetPerson = TypicalPersons.ALICE;
        LoanUnpaidCommand loanUnpaidCommand = new LoanUnpaidCommand(
                new ArrayList<Index>(), Collections.singletonList(targetPerson));

        assertThrows(CommandException.class, () -> loanUnpaidCommand.execute(model));
    }

    @Test
    public void execute_validTargetPerson_unpaidSuccess() throws CommandException {
        Person targetPerson = TypicalLoans.ZED_IN_PAID.getPerson();
        LoanUnpaidCommand loanUnpaidCommand = new LoanUnpaidCommand(
                new ArrayList<Index>(), Collections.singletonList(targetPerson));

        String expectedMessage = String.format(
                LoanUnpaidCommand.MESSAGE_SUCCESS, String.format("%d", TypicalIndexes.INDEX_FIRST_ITEM.getOneBased()));
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.LOAN);

        assertCommandSuccess(loanUnpaidCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() throws CommandException {
        LoanUnpaidCommand firstLoanUnpaidCommand = new LoanUnpaidCommand(
                Collections.singletonList(TypicalIndexes.INDEX_FIRST_ITEM), new ArrayList<Person>());
        LoanUnpaidCommand secondLoanUnpaidCommand = new LoanUnpaidCommand(
                Collections.singletonList(TypicalIndexes.INDEX_SECOND_ITEM), new ArrayList<Person>());

        // same object -> returns true
        assertEquals(firstLoanUnpaidCommand, firstLoanUnpaidCommand);

        // same values -> returns true
        LoanUnpaidCommand firstLoanUnpaidCommandCopy = new LoanUnpaidCommand(
                Collections.singletonList(TypicalIndexes.INDEX_FIRST_ITEM), new ArrayList<Person>());
        assertEquals(firstLoanUnpaidCommand, firstLoanUnpaidCommandCopy);

        // different types -> returns false
        assertNotEquals(firstLoanUnpaidCommand, 5);

        // null -> returns false
        assertNotEquals(firstLoanUnpaidCommand, null);

        // different targets -> returns false
        assertNotEquals(firstLoanUnpaidCommand, secondLoanUnpaidCommand);
    }
}
