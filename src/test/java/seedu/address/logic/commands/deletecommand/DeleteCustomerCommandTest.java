package seedu.address.logic.commands.deletecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCustomerAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;
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
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCustomerCommand}.
 */
public class DeleteCustomerCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        DeleteCustomerCommand deleteCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);

        String expectedMessage = String.format(DeleteCustomerCommand.MESSAGE_DELETE_CUSTOMER_SUCCESS, customerToDelete);

        Model expectedModel = new ModelManager(new DataBook<Customer>(model.getCustomerBook()), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.deleteCustomer(customerToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        DeleteCustomerCommand deleteCommand = new DeleteCustomerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        DeleteCustomerCommand deleteCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);

        String expectedMessage = String.format(DeleteCustomerCommand.MESSAGE_DELETE_CUSTOMER_SUCCESS, customerToDelete);

        Model expectedModel = new ModelManager(new DataBook<Customer>(model.getCustomerBook()), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.deleteCustomer(customerToDelete);
        showNoCustomer(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        Index outOfBoundIndex = INDEX_SECOND_CUSTOMER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCustomerBook().getList().size());

        DeleteCustomerCommand deleteCommand = new DeleteCustomerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCustomerCommand deleteFirstCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);
        DeleteCustomerCommand deleteSecondCommand = new DeleteCustomerCommand(INDEX_SECOND_CUSTOMER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCustomerCommand deleteFirstCommandCopy = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCustomer(Model model) {
        model.updateFilteredCustomerList(c -> false);

        assertTrue(model.getFilteredCustomerList().isEmpty());
    }
}
