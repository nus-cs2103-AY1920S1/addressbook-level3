package seedu.address.logic.commands.copycommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CopyCustomerCommand}.
 */
public class CopyCustomerCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success()
            throws IOException, UnsupportedFlavorException, CommandException {
        Customer customerToCopy = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        CopyCustomerCommand copyCommand = new CopyCustomerCommand(INDEX_FIRST_CUSTOMER);

        String expectedMessage = String.format(CopyCustomerCommand.MESSAGE_COPY_CUSTOMER_SUCCESS, customerToCopy);

        Model expectedModel = new ModelManager(new DataBook<Customer>(model.getCustomerBook()), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        copyCommand.execute(model, new CommandHistory(), new UndoRedoStack());

        String data = (String) Toolkit.getDefaultToolkit()
                .getSystemClipboard().getData(DataFlavor.stringFlavor);

        assertEquals(data, customerToCopy.toString());
        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        CopyCustomerCommand copyCommand = new CopyCustomerCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        Customer customerToCopy = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        CopyCustomerCommand copyCommand = new CopyCustomerCommand(INDEX_FIRST_CUSTOMER);

        String expectedMessage = String.format(CopyCustomerCommand.MESSAGE_COPY_CUSTOMER_SUCCESS, customerToCopy);

        Model expectedModel = new ModelManager(new DataBook<Customer>(model.getCustomerBook()), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        showCustomerAtIndex(expectedModel, INDEX_FIRST_CUSTOMER);
        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        Index outOfBoundIndex = INDEX_SECOND_CUSTOMER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCustomerBook().getList().size());

        CopyCustomerCommand copyCommand = new CopyCustomerCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CopyCustomerCommand copyFirstCommand = new CopyCustomerCommand(INDEX_FIRST_CUSTOMER);
        CopyCustomerCommand copySecondCommand = new CopyCustomerCommand(INDEX_SECOND_CUSTOMER);

        // same object -> returns true
        assertTrue(copyFirstCommand.equals(copyFirstCommand));

        // same values -> returns true
        CopyCustomerCommand copyFirstCommandCopy = new CopyCustomerCommand(INDEX_FIRST_CUSTOMER);
        assertTrue(copyFirstCommand.equals(copyFirstCommandCopy));

        // different types -> returns false
        assertFalse(copyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(copyFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(copyFirstCommand.equals(copySecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCustomer(Model model) {
        model.updateFilteredCustomerList(c -> false);

        assertTrue(model.getFilteredCustomerList().isEmpty());
    }
}
