package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Test
    public void equals() throws ParseException {
        final SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM/yyyy");

        Calendar firstMonthYear = Calendar.getInstance();
        firstMonthYear.setTime(monthYearFormat.parse("10/2019"));

        Calendar secondMonthYear = Calendar.getInstance();
        firstMonthYear.setTime(monthYearFormat.parse("11/2019"));

        ListCommand listFirstCommand = new ListCommand(firstMonthYear);
        ListCommand listSecondCommand = new ListCommand(secondMonthYear);
        ListCommand listThirdCommand = new ListCommand();

        // same object -> returns true
        assertTrue(listFirstCommand.equals(listFirstCommand));
        assertTrue(listThirdCommand.equals(listThirdCommand));

        // different object -> returns false
        ListCommand listFirstCommandCopy = new ListCommand(firstMonthYear);
        assertFalse(listFirstCommand.equals(listFirstCommandCopy));

        ListCommand listThirdCommandCopy = new ListCommand();
        assertFalse(listThirdCommand.equals(listThirdCommandCopy));

        // different types -> returns false
        assertFalse(listFirstCommand.equals(1));
        assertFalse(listFirstCommand.equals(listThirdCommand));

        // null -> returns false
        assertFalse(listFirstCommand.equals(null));
        assertFalse(listThirdCommand.equals(null));

        // different transaction -> returns false
        assertFalse(listFirstCommand.equals(listSecondCommand));
        assertFalse(listFirstCommand.equals(listThirdCommand));
    }
}
