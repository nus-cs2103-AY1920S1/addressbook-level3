package seedu.address.logic.commands.deletecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SCHEDULE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.schedule.Schedule;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteScheduleCommand}.
 */
public class DeleteScheduleCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteScheduleCommand(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Schedule scheduleToDelete = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());
        DeleteScheduleCommand deleteCommand = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);

        String expectedMessage = String.format(DeleteScheduleCommand.MESSAGE_DELETE_SCHEDULE_SUCCESS, scheduleToDelete);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), new DataBook<Schedule>(model.getScheduleBook()), new DataBook<Order>(),
                new UserPrefs());
        expectedModel.deleteSchedule(scheduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteScheduleCommand deleteCommand = new DeleteScheduleCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    // Filtered list tests to be updated after predicates are set up

    @Test
    public void equals() {
        DeleteScheduleCommand deleteFirstCommand = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);
        DeleteScheduleCommand deleteSecondCommand = new DeleteScheduleCommand(INDEX_SECOND_SCHEDULE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteScheduleCommand deleteFirstCommandCopy = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different schedule -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }


}
