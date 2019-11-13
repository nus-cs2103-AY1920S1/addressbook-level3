package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static budgetbuddy.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanFilters;
import budgetbuddy.model.loan.LoanSorters;
import budgetbuddy.model.loan.Status;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class LoanListCommandTest {

    private final String successSorted = String.format("%s %s",
            LoanListCommand.MESSAGE_SUCCESS, LoanListCommand.MESSAGE_SORTED);
    private final String successFiltered = String.format("%s %s",
            LoanListCommand.MESSAGE_SUCCESS, LoanListCommand.MESSAGE_FILTERED);
    private final String successSortedFiltered = String.format("%s %s %s",
            LoanListCommand.MESSAGE_SUCCESS, LoanListCommand.MESSAGE_SORTED, LoanListCommand.MESSAGE_FILTERED);

    private Model emptyModel = new ModelManager();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @BeforeEach
    public void initialize() {
        emptyModel = new ModelManager();
        model = new ModelManager();
        expectedModel = new ModelManager();
        for (Loan loan : TypicalLoans.LOAN_LIST) {
            model.getLoansManager().addLoan(loan);
            expectedModel.getLoansManager().addLoan(loan);
        }
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoanListCommand(null, null));
        assertThrows(NullPointerException.class, () -> new LoanListCommand(null, new ArrayList<>()));
        assertThrows(NullPointerException.class, () -> new LoanListCommand(Optional.empty(), null));
    }

    @Test
    public void execute_emptyList_listSuccess() {
        LoanListCommand loanListCommand = new LoanListCommand(Optional.empty(), new ArrayList<>());
        String expectedMessage = LoanListCommand.MESSAGE_NO_LOANS;
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandCategory.LOAN);
        assertCommandSuccess(loanListCommand, emptyModel, expectedCommandResult, emptyModel);
    }

    @Test
    public void execute_normalList_listSuccess() {
        LoanListCommand loanListCommand = new LoanListCommand(Optional.empty(), new ArrayList<>());
        String expectedMessage = LoanListCommand.MESSAGE_SUCCESS;
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandCategory.LOAN);
        assertCommandSuccess(loanListCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_sorterGiven_listSortSuccess() {
        Comparator<Loan> sorter = LoanSorters.PERSON;
        LoanListCommand loanListCommand = new LoanListCommand(Optional.of(sorter), new ArrayList<>());

        CommandResult expectedCommandResult = new CommandResult(successSorted, CommandCategory.LOAN);
        expectedModel.getLoansManager().sortLoans(sorter);

        assertCommandSuccess(loanListCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_filterGiven_listFilterSuccess() {
        Predicate<Loan> filter = LoanFilters.getDirectionPredicate(Direction.IN);
        LoanListCommand loanListCommand =
                new LoanListCommand(Optional.empty(), List.of(filter));

        CommandResult expectedCommandResult = new CommandResult(successFiltered, CommandCategory.LOAN);
        expectedModel.getLoansManager().updateFilteredList(filter);

        assertCommandSuccess(loanListCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_filtersGiven_listFiltersSuccess() {
        Predicate<Loan> filterDirection = LoanFilters.getDirectionPredicate(Direction.IN);
        Predicate<Loan> filterStatus = LoanFilters.getStatusPredicate(Status.UNPAID);
        LoanListCommand loanListCommand =
                new LoanListCommand(Optional.empty(), List.of(filterDirection, filterStatus));

        CommandResult expectedCommandResult = new CommandResult(successFiltered, CommandCategory.LOAN);
        Predicate<Loan> filterDirectionAndStatus = filterDirection.and(filterStatus);
        expectedModel.getLoansManager().updateFilteredList(filterDirectionAndStatus);

        assertCommandSuccess(loanListCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_sorterAndFilterGiven_listSortedFilteredSuccess() {
        Comparator<Loan> sorter = LoanSorters.PERSON;
        Predicate<Loan> filterDirection = LoanFilters.getDirectionPredicate(Direction.IN);
        LoanListCommand loanListCommand = new LoanListCommand(Optional.of(sorter), List.of(filterDirection));

        CommandResult expectedCommandResult = new CommandResult(successSortedFiltered, CommandCategory.LOAN);
        expectedModel.getLoansManager().sortLoans(sorter);
        expectedModel.getLoansManager().updateFilteredList(filterDirection);

        assertCommandSuccess(loanListCommand, model, expectedCommandResult, expectedModel);
    }
}
