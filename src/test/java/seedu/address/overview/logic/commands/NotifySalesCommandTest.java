package seedu.address.overview.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NOTIFY_AMOUNT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_SALES_SUCCESS;

import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
import seedu.address.overview.model.Model;
import seedu.address.overview.model.ModelManager;
import seedu.address.testutil.TypicalOverview;
import seedu.address.util.CommandResult;

public class NotifySalesCommandTest {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private Model blankOverviewModel = new ModelManager(TypicalOverview.BLANK_OVERVIEW_MODEL);

    @Test
    public void notifySalesCommand_correctInput_success() {
        NotifySalesCommand command = new NotifySalesCommand(80);
        assertTrue(command.amount == 80.0);

        try {
            assertEquals(new CommandResult(String.format(MESSAGE_NOTIFY_SALES_SUCCESS,
                    DECIMAL_FORMAT.format(80.00))),
                    command.execute(blankOverviewModel));
        } catch (InvalidValueException e) {
            throw new AssertionError("This should not happen");
        }
    }

    @Test
    public void notifySalesCommand_belowRange_throwsInvalidValueException() {
        NotifySalesCommand command = new NotifySalesCommand(-20);
        try {
            command.execute(blankOverviewModel);
        } catch (InvalidValueException e) {
            assertEquals(new InvalidValueException(MESSAGE_INVALID_NOTIFY_AMOUNT).getMessage(), e.getMessage());
        }
    }

    @Test
    public void notifySalesCommand_aboveRange_throwsInvalidValueException() {
        NotifySalesCommand command = new NotifySalesCommand(200);
        try {
            command.execute(blankOverviewModel);
        } catch (InvalidValueException e) {
            assertEquals(new InvalidValueException(MESSAGE_INVALID_NOTIFY_AMOUNT).getMessage(), e.getMessage());
        }
    }

    @Test
    public void testIfEqual_equalAmount_true() {
        NotifySalesCommand command1 = new NotifySalesCommand(70);
        NotifySalesCommand command2 = new NotifySalesCommand(70);
        assertEquals(command1, command2);
    }

    @Test
    public void testIfEqual_diffAmount_false() {
        NotifySalesCommand command1 = new NotifySalesCommand(50);
        NotifySalesCommand command2 = new NotifySalesCommand(70);
        assertNotEquals(command1, command2);
    }
}
