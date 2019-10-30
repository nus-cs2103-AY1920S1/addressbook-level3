package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CompleteOrderCommand}.
 */
public class CompleteOrderCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToComplete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        CompleteOrderCommand completeCommand = new CompleteOrderCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(CompleteOrderCommand.MESSAGE_COMPLETE_ORDER_SUCCESS, orderToComplete);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.deleteOrder(orderToComplete);
        expectedModel.deletePhone(orderToComplete.getPhone());

        UUID id = orderToComplete.getId();
        Customer customer = orderToComplete.getCustomer();
        Phone phone = orderToComplete.getPhone();
        Price price = orderToComplete.getPrice();
        Optional<Schedule> schedule = orderToComplete.getSchedule();
        Set<Tag> tags = orderToComplete.getTags();
        Order completedOrder = new Order(id, customer, phone, price, Status.COMPLETED, schedule, tags);

        expectedModel.addArchivedOrder(completedOrder);

        assertCommandSuccess(completeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        CompleteOrderCommand completeCommand = new CompleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(completeCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderToComplete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        CompleteOrderCommand completeCommand = new CompleteOrderCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(CompleteOrderCommand.MESSAGE_COMPLETE_ORDER_SUCCESS, orderToComplete);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.deleteOrder(orderToComplete);
        expectedModel.deletePhone(orderToComplete.getPhone());

        UUID id = orderToComplete.getId();
        Customer customer = orderToComplete.getCustomer();
        Phone phone = orderToComplete.getPhone();
        Price price = orderToComplete.getPrice();
        Optional<Schedule> schedule = orderToComplete.getSchedule();
        Set<Tag> tags = orderToComplete.getTags();
        Order completedOrder = new Order(id, customer, phone, price, Status.COMPLETED, schedule, tags);

        expectedModel.addArchivedOrder(completedOrder);
        showNoOrder(expectedModel);

        assertCommandSuccess(completeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getList().size());

        CompleteOrderCommand completeOrderCommand = new CompleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(completeOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CompleteOrderCommand completeFirstCommand = new CompleteOrderCommand(INDEX_FIRST_ORDER);
        CompleteOrderCommand completeSecondCommand = new CompleteOrderCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(completeFirstCommand.equals(completeFirstCommand));

        // same values -> returns true
        CompleteOrderCommand completeFirstCommandCopy = new CompleteOrderCommand(INDEX_FIRST_ORDER);
        assertTrue(completeFirstCommand.equals(completeFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completeFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(completeFirstCommand.equals(completeSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);

        assertTrue(model.getFilteredOrderList().isEmpty());
    }
}
