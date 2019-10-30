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
 * {@code CancelOrderCommand}.
 */
public class CancelOrderCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToCancel = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        CancelOrderCommand cancelCommand = new CancelOrderCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(CancelOrderCommand.MESSAGE_CANCEL_ORDER_SUCCESS, orderToCancel);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.deleteOrder(orderToCancel);

        UUID id = orderToCancel.getId();
        Customer customer = orderToCancel.getCustomer();
        Phone phone = orderToCancel.getPhone();
        Price price = orderToCancel.getPrice();
        Optional<Schedule> schedule = orderToCancel.getSchedule();
        Set<Tag> tags = orderToCancel.getTags();
        Order cancelledOrder = new Order(id, customer, phone, price, Status.CANCELLED, schedule, tags);

        expectedModel.addArchivedOrder(cancelledOrder);

        assertCommandSuccess(cancelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        CancelOrderCommand cancelCommand = new CancelOrderCommand(outOfBoundIndex);

        assertCommandFailure(cancelCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderToCancel = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        CancelOrderCommand cancelCommand = new CancelOrderCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(CancelOrderCommand.MESSAGE_CANCEL_ORDER_SUCCESS, orderToCancel);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.deleteOrder(orderToCancel);

        UUID id = orderToCancel.getId();
        Customer customer = orderToCancel.getCustomer();
        Phone phone = orderToCancel.getPhone();
        Price price = orderToCancel.getPrice();
        Optional<Schedule> schedule = orderToCancel.getSchedule();
        Set<Tag> tags = orderToCancel.getTags();
        Order cancelledOrder = new Order(id, customer, phone, price, Status.CANCELLED, schedule, tags);

        expectedModel.addArchivedOrder(cancelledOrder);
        showNoOrder(expectedModel);

        assertCommandSuccess(cancelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getList().size());

        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(outOfBoundIndex);

        assertCommandFailure(cancelOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CancelOrderCommand cancelFirstCommand = new CancelOrderCommand(INDEX_FIRST_ORDER);
        CancelOrderCommand cancelSecondCommand = new CancelOrderCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(cancelFirstCommand.equals(cancelFirstCommand));

        // same values -> returns true
        CancelOrderCommand cancelFirstCommandCopy = new CancelOrderCommand(INDEX_FIRST_ORDER);
        assertTrue(cancelFirstCommand.equals(cancelFirstCommandCopy));

        // different types -> returns false
        assertFalse(cancelFirstCommand.equals(1));

        // null -> returns false
        assertFalse(cancelFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(cancelFirstCommand.equals(cancelSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);

        assertTrue(model.getFilteredOrderList().isEmpty());
    }
}
