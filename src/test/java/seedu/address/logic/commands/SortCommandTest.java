package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTransactions.getTypicalUnsortedUserState;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.comparator.AmountComparator;
import seedu.address.logic.comparator.DateComparator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.ui.tab.Tab;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {
    private Model model;
    private Model expectedSortedByAmountModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalUnsortedUserState(), new UserPrefs());
        expectedSortedByAmountModel = new ModelManager(getTypicalUnsortedUserState(), new UserPrefs());
    }

    @Test
    public void execute_sortByAmount_success() {
        AmountComparator amountComparator = new AmountComparator();
        List<BankAccountOperation> sortedTransactionHistory =
            expectedSortedByAmountModel.getBankAccount().getSortedTransactionHistory(amountComparator);
        expectedSortedByAmountModel.setTransactions(sortedTransactionHistory);
        expectedSortedByAmountModel.commitUserState();
        assertCommandSuccess(new SortCommand(amountComparator), model,
            new CommandResult(SortCommand.MESSAGE_SUCCESS, false, false, Tab.TRANSACTION),
            expectedSortedByAmountModel);
    }

    @Test
    public void execute_sortByDate_success() {
        DateComparator dateComparator = new DateComparator();
        List<BankAccountOperation> sortedTransactionHistory =
            expectedSortedByAmountModel.getBankAccount().getSortedTransactionHistory(dateComparator);
        expectedSortedByAmountModel.setTransactions(sortedTransactionHistory);
        expectedSortedByAmountModel.commitUserState();
        assertCommandSuccess(new SortCommand(dateComparator), model,
            new CommandResult(SortCommand.MESSAGE_SUCCESS, false, false, Tab.TRANSACTION),
            expectedSortedByAmountModel);
    }

    @Test
    public void equals() {
        SortCommand amountSort = new SortCommand(new AmountComparator());
        SortCommand dateSort = new SortCommand(new DateComparator());

        // same object -> returns true
        assertTrue(amountSort.equals(amountSort));

        // same values -> returns true
        assertTrue(amountSort.equals(new SortCommand(new AmountComparator())));

        // different types -> returns false
        assertFalse(amountSort.equals("1"));

        // null -> returns false
        assertFalse(amountSort.equals(null));

        // different sort -> returns false
        assertFalse(amountSort.equals(dateSort));
    }
}
