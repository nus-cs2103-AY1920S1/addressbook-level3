package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEDGER_OPERATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalUserState(), new UserPrefs());
        expectedModel = new ModelManager(model.getUserState(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        expectedModel.updateFilteredLedgerList(PREDICATE_SHOW_ALL_LEDGER_OPERATIONS);
        expectedModel.commitUserState();
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
