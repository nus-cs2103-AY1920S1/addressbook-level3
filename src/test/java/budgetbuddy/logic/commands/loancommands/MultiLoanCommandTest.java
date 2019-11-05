package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static budgetbuddy.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Since {@code MultiLoanCommand} is an abstract class, {@code LoanPaidCommand} is used to test it.
 */
public class MultiLoanCommandTest {
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
    public void checkTargetLists_emptyLists_throwsCommandException() {
        assertThrows(CommandException.class, () -> new LoanPaidCommand(new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    public void checkTargetLists_duplicateLoanIndices_throwsCommandException() {
        List<Index> loanIndices = List.of(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_FIRST_ITEM);
        assertThrows(CommandException.class, () -> new LoanPaidCommand(loanIndices, new ArrayList<>()));
    }

    @Test
    public void checkTargetLists_duplicatePersons_throwsCommandException() {
        List<Person> persons = List.of(TypicalPersons.ALICE, TypicalPersons.ALICE);
        assertThrows(CommandException.class, () -> new LoanPaidCommand(new ArrayList<>(), persons));
    }

    @Test
    public void constructMultiLoanResult_missingLoanIndices_listsMissingIndices() throws CommandException {
        List<Index> loanIndices = List.of(TypicalIndexes.INDEX_SECOND_ITEM);
        List<Person> persons = List.of(TypicalLoans.JOHN_OUT_UNPAID.getPerson());
        LoanPaidCommand loanPaidCommand = new LoanPaidCommand(loanIndices, persons);

        String expectedMessage =
                String.format(
                        LoanPaidCommand.MESSAGE_SUCCESS,
                        String.format("%d", TypicalIndexes.INDEX_FIRST_ITEM.getOneBased()))
                + String.format(
                        MultiLoanCommand.MESSAGE_MISSING_LOAN_INDICES,
                        String.format("%d", TypicalIndexes.INDEX_SECOND_ITEM.getOneBased()));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandCategory.LOAN);

        assertCommandSuccess(loanPaidCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void constructMultiLoanResult_missingPersons_listMissingPersons() throws CommandException {
        List<Index> loanIndices = List.of(TypicalIndexes.INDEX_FIRST_ITEM);
        List<Person> persons = List.of(TypicalLoans.MARY_IN_UNPAID.getPerson());
        LoanPaidCommand loanPaidCommand = new LoanPaidCommand(loanIndices, persons);

        String expectedMessage =
                String.format(
                        LoanPaidCommand.MESSAGE_SUCCESS,
                        String.format("%d", TypicalIndexes.INDEX_FIRST_ITEM.getOneBased()))
                        + String.format(
                        MultiLoanCommand.MESSAGE_MISSING_PERSONS,
                        TypicalLoans.MARY_IN_UNPAID.getPerson().getName());
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandCategory.LOAN);

        assertCommandSuccess(loanPaidCommand, model, expectedCommandResult, expectedModel);
    }
}
