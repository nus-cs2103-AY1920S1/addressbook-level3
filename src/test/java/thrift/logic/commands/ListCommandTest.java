package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.UserPrefs;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());
        expectedModel = new ModelManager(model.getThrift(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFilteredWithMonthYear_success() {
        Calendar calendar = Calendar.getInstance();
        assertEquals(new ListCommand(calendar).execute(model).getFeedbackToUser(),
                ListCommand.MESSAGE_SUCCESS_MONTH_FILTER);
    }
}
