package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTransactions.getTypicalUnsortedUserState;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.comparator.AmountComparator;
import seedu.address.logic.comparator.DateComparator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {
    private Model model;
    private Model expectedSortedByAmountModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalUserState(), new UserPrefs());
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
            SortCommand.MESSAGE_SUCCESS, expectedSortedByAmountModel);
    }

    @Test
    public void execute_sortByDate_success() {
        DateComparator dateComparator = new DateComparator();
        List<BankAccountOperation> sortedTransactionHistory =
            expectedSortedByAmountModel.getBankAccount().getSortedTransactionHistory(dateComparator);
        expectedSortedByAmountModel.setTransactions(sortedTransactionHistory);
        expectedSortedByAmountModel.commitUserState();
        assertCommandSuccess(new SortCommand(dateComparator), model,
            SortCommand.MESSAGE_SUCCESS, expectedSortedByAmountModel);
    }
}
