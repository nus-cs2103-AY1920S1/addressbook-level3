package seedu.address.overview.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NOTIFY_AMOUNT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_BUDGET_SUCCESS;

import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
import seedu.address.overview.model.Model;
import seedu.address.overview.model.ModelManager;
import seedu.address.testutil.TypicalOverview;
import seedu.address.util.CommandResult;

public class NotifyBudgetCommandTest {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private Model blankOverviewModel = new ModelManager(TypicalOverview.BLANK_OVERVIEW_MODEL);

    @Test
    public void notifyBudgetCommand_correctInput_success() {
        NotifyBudgetCommand command = new NotifyBudgetCommand(80);
        assertTrue(command.amount == 80.0);

        try {
            assertEquals(new CommandResult(String.format(MESSAGE_NOTIFY_BUDGET_SUCCESS,
                    DECIMAL_FORMAT.format(80.00))),
                    command.execute(blankOverviewModel));
        } catch (InvalidValueException e) {
            throw new AssertionError("This should not happen");
        }
    }

    @Test
    public void notifyBudgetCommand_belowRange_throwsInvalidValueException() {
        NotifyBudgetCommand command = new NotifyBudgetCommand(-20);
        try {
            command.execute(blankOverviewModel);
        } catch (InvalidValueException e) {
            assertEquals(new InvalidValueException(MESSAGE_INVALID_NOTIFY_AMOUNT).getMessage(), e.getMessage());
        }
    }

    @Test
    public void notifyBudgetCommand_aboveRange_throwsInvalidValueException() {
        NotifyBudgetCommand command = new NotifyBudgetCommand(200);
        try {
            command.execute(blankOverviewModel);
        } catch (InvalidValueException e) {
            assertEquals(new InvalidValueException(MESSAGE_INVALID_NOTIFY_AMOUNT).getMessage(), e.getMessage());
        }
    }

    @Test
    public void testIfEqual_equalAmount_true() {
        NotifyBudgetCommand command1 = new NotifyBudgetCommand(70);
        NotifyBudgetCommand command2 = new NotifyBudgetCommand(70);
        assertEquals(command1, command2);
    }

    @Test
    public void testIfEqual_diffAmount_false() {
        NotifyBudgetCommand command1 = new NotifyBudgetCommand(50);
        NotifyBudgetCommand command2 = new NotifyBudgetCommand(70);
        assertNotEquals(command1, command2);
    }

}
