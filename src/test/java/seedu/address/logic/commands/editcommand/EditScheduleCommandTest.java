package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EVERYDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.FRIDAY_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.MONDAY_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import java.util.Calendar;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.editcommand.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.EditScheduleDescriptorBuilder;
import seedu.address.testutil.ScheduleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditScheduleCommand.
 */
public class EditScheduleCommandTest {

    private static final EditScheduleDescriptor VALID_DESCRIPTOR = new EditScheduleDescriptor();
    private static final boolean VALID_ALLOW = true;

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new EditScheduleCommand(null, VALID_DESCRIPTOR, VALID_ALLOW));
    }

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new EditScheduleCommand(INDEX_FIRST_ORDER, null, VALID_ALLOW));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Schedule editedSchedule = new ScheduleBuilder().build();
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder(editedSchedule).build();
        EditScheduleCommand editCommand = new EditScheduleCommand(INDEX_FIRST_ORDER, descriptor, VALID_ALLOW);

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), new DataBook<Schedule>(model.getScheduleBook()),
                new DataBook<Order>(), new UserPrefs());
        expectedModel.setSchedule(model.getFilteredScheduleList().get(0), editedSchedule);

        assertEquals(expectedMessage, editCommand.execute(expectedModel, new CommandHistory(),
                new UndoRedoStack()).getFeedbackToUser());
    }

    @Test
    public void execute_allFieldsButOrderIsCompleted_throwsCommandException() {
        Schedule editedSchedule = new ScheduleBuilder().build();
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder(editedSchedule).build();
        EditScheduleCommand editCommand = new EditScheduleCommand(INDEX_THIRD_ORDER, descriptor, VALID_ALLOW);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), new DataBook<Schedule>(model.getScheduleBook()),
                new DataBook<Order>(), new UserPrefs());
        expectedModel.setSchedule(model.getFilteredScheduleList().get(0), editedSchedule);

        assertThrows(CommandException.class, () -> editCommand.execute(expectedModel, new CommandHistory(),
                new UndoRedoStack()));
    }

    @Test
    public void execute_allFieldsButOrderIsUnscheduled_throwsCommandException() {
        Schedule editedSchedule = new ScheduleBuilder().build();
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder(editedSchedule).build();
        EditScheduleCommand editCommand = new EditScheduleCommand(INDEX_FOURTH_ORDER, descriptor, VALID_ALLOW);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), new DataBook<Schedule>(model.getScheduleBook()),
                new DataBook<Order>(), new UserPrefs());
        expectedModel.setSchedule(model.getFilteredScheduleList().get(0), editedSchedule);

        assertThrows(CommandException.class, () -> editCommand.execute(expectedModel, new CommandHistory(),
                new UndoRedoStack()));
    }

    @Test
    public void execute_editedScheduleClashesWithNoAllow_throwsCommandException() {
        // New calendar clashes with existing schedule in schedulebook
        Calendar newCalendar = (Calendar) FRIDAY_SCHEDULE.getCalendar().clone();
        newCalendar.add(Calendar.MINUTE, 20);

        // New schedule has a different UUID, else it will trigger duplicate exception
        Schedule editedSchedule = new ScheduleBuilder(FRIDAY_SCHEDULE).withId(UUID.randomUUID())
                .withCalendar(newCalendar).build();
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder(editedSchedule).build();
        EditScheduleCommand editCommand = new EditScheduleCommand(INDEX_FIRST_ORDER, descriptor, false);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), new DataBook<Schedule>(model.getScheduleBook()),
                new DataBook<Order>(), new UserPrefs());
        expectedModel.setSchedule(model.getFilteredScheduleList().get(0), editedSchedule);

        assertThrows(CommandException.class, () -> editCommand.execute(expectedModel, new CommandHistory(),
                new UndoRedoStack()));
    }

    @Test
    public void execute_editedScheduleClashesWithAllow_success() throws CommandException {
        Schedule editedSchedule = new ScheduleBuilder(MONDAY_SCHEDULE).build();
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder(editedSchedule).build();
        EditScheduleCommand editCommand = new EditScheduleCommand(INDEX_FIRST_ORDER, descriptor, VALID_ALLOW);

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), new DataBook<Schedule>(model.getScheduleBook()),
                new DataBook<Order>(), new UserPrefs());
        expectedModel.setSchedule(model.getFilteredScheduleList().get(0), editedSchedule);

        assertEquals(expectedMessage, editCommand.execute(expectedModel, new CommandHistory(),
                new UndoRedoStack()).getFeedbackToUser());
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Index indexFirstSchedule = Index.fromOneBased(1);
        Index indexFirstOrder = indexFirstSchedule;
        Schedule lastSchedule = model.getFilteredScheduleList().get(indexFirstSchedule.getZeroBased());

        ScheduleBuilder scheduleInList = new ScheduleBuilder(lastSchedule);
        Schedule editedSchedule = scheduleInList.withCalendar(VALID_CALENDAR_MONDAY).withVenue(VALID_VENUE_MONDAY)
                .withTags(VALID_TAG_EVERYDAY).build();

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_CALENDAR_MONDAY)
                .withTime(VALID_CALENDAR_MONDAY).withVenue(VALID_VENUE_MONDAY).withTags(VALID_TAG_EVERYDAY).build();
        EditScheduleCommand editCommand = new EditScheduleCommand(indexFirstOrder, descriptor, VALID_ALLOW);

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), new DataBook<Schedule>(model.getScheduleBook()),
                new DataBook<Order>(), new UserPrefs());
        expectedModel.setSchedule(lastSchedule, editedSchedule);

        assertEquals(expectedMessage, editCommand.execute(expectedModel, new CommandHistory(),
                new UndoRedoStack()).getFeedbackToUser());
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditScheduleCommand editCommand = new EditScheduleCommand(INDEX_FIRST_ORDER,
                new EditScheduleDescriptor(), VALID_ALLOW);
        Schedule editedSchedule = model.getFilteredScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, editedSchedule);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), new DataBook<Schedule>(model.getScheduleBook()),
                new DataBook<Order>(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withDate(VALID_CALENDAR_MONDAY).build();
        EditScheduleCommand editCommand = new EditScheduleCommand(outOfBoundIndex, descriptor, VALID_ALLOW);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditScheduleCommand standardCommand =
                new EditScheduleCommand(INDEX_FIRST_ORDER, DESC_MONDAY, VALID_ALLOW);

        // same values -> returns true
        EditScheduleDescriptor copyDescriptor = new EditScheduleDescriptor(DESC_MONDAY);
        EditScheduleCommand commandWithSameValues =
                new EditScheduleCommand(INDEX_FIRST_ORDER, copyDescriptor, VALID_ALLOW);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditScheduleCommand(INDEX_SECOND_ORDER, DESC_MONDAY, VALID_ALLOW)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditScheduleCommand(INDEX_FIRST_ORDER, DESC_FRIDAY, VALID_ALLOW)));

        // different boolean allow -> returns false
        assertFalse(standardCommand.equals(new EditScheduleCommand(INDEX_FIRST_ORDER, DESC_MONDAY, false)));
    }

}
