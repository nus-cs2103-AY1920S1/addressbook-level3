package seedu.address.logic.finance.commands;

import static seedu.address.logic.finance.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLogEntries.getTypicalFinanceLog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.finance.Model;
import seedu.address.model.finance.ModelFinanceManager;
import seedu.address.model.finance.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model financeModel;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        financeModel = new ModelFinanceManager(getTypicalFinanceLog(), new UserPrefs());
        expectedModel = new ModelFinanceManager(financeModel.getFinanceLog(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), financeModel, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
