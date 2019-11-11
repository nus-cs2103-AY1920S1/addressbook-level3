package seedu.address.overview.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NOTIFY_AMOUNT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_EXPENSE_SUCCESS;

import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
import seedu.address.overview.model.Model;
import seedu.address.overview.model.ModelManager;
import seedu.address.testutil.TypicalOverview;
import seedu.address.util.CommandResult;

public class NotifyExpenseCommandTest {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private Model blankOverviewModel = new ModelManager(TypicalOverview.BLANK_OVERVIEW_MODEL);

    @Test
    public void notifyExpenseCommand_correctInput_success() {
        NotifyExpenseCommand command = new NotifyExpenseCommand(80);
        assertTrue(command.amount == 80.0);

        try {
            assertEquals(new CommandResult(String.format(MESSAGE_NOTIFY_EXPENSE_SUCCESS,
                    DECIMAL_FORMAT.format(80.00))),
                    command.execute(blankOverviewModel));
        } catch (InvalidValueException e) {
            throw new AssertionError("This should not happen");
        }
    }

    @Test
    public void notifyExpenseCommand_belowRange_throwsInvalidValueException() {
        NotifyExpenseCommand command = new NotifyExpenseCommand(-20);
        try {
            command.execute(blankOverviewModel);
        } catch (InvalidValueException e) {
            assertEquals(new InvalidValueException(MESSAGE_INVALID_NOTIFY_AMOUNT).getMessage(), e.getMessage());
        }
    }

    @Test
    public void notifyExpenseCommand_aboveRange_throwsInvalidValueException() {
        NotifyExpenseCommand command = new NotifyExpenseCommand(200);
        try {
            command.execute(blankOverviewModel);
        } catch (InvalidValueException e) {
            assertEquals(new InvalidValueException(MESSAGE_INVALID_NOTIFY_AMOUNT).getMessage(), e.getMessage());
        }
    }

    @Test
    public void testIfEqual_equalAmount_true() {
        NotifyExpenseCommand command1 = new NotifyExpenseCommand(70);
        NotifyExpenseCommand command2 = new NotifyExpenseCommand(70);
        assertEquals(command1, command2);
    }

    @Test
    public void testIfEqual_diffAmount_false() {
        NotifyExpenseCommand command1 = new NotifyExpenseCommand(50);
        NotifyExpenseCommand command2 = new NotifyExpenseCommand(70);
        assertNotEquals(command1, command2);
    }
}
