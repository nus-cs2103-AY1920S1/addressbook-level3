package seedu.address.logic.commands.copycommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import seedu.address.model.order.Order;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CopyOrderCommand}.
 */
public class CopyOrderCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success()
            throws IOException, UnsupportedFlavorException, CommandException {
        Order orderToCopy = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        CopyOrderCommand copyCommand = new CopyOrderCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(CopyOrderCommand.MESSAGE_COPY_ORDER_SUCCESS, orderToCopy);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        copyCommand.execute(model, new CommandHistory(), new UndoRedoStack());

        String data = (String) Toolkit.getDefaultToolkit()
                .getSystemClipboard().getData(DataFlavor.stringFlavor);

        assertEquals(data, orderToCopy.toString());
        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        CopyOrderCommand copyCommand = new CopyOrderCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderToCopy = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        CopyOrderCommand copyCommand = new CopyOrderCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(CopyOrderCommand.MESSAGE_COPY_ORDER_SUCCESS, orderToCopy);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        showOrderAtIndex(expectedModel, INDEX_FIRST_ORDER);
        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getList().size());

        CopyOrderCommand copyCommand = new CopyOrderCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CopyOrderCommand copyFirstCommand = new CopyOrderCommand(INDEX_FIRST_ORDER);
        CopyOrderCommand copySecondCommand = new CopyOrderCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(copyFirstCommand.equals(copyFirstCommand));

        // same values -> returns true
        CopyOrderCommand copyFirstCommandCopy = new CopyOrderCommand(INDEX_FIRST_ORDER);
        assertTrue(copyFirstCommand.equals(copyFirstCommandCopy));

        // different types -> returns false
        assertFalse(copyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(copyFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(copyFirstCommand.equals(copySecondCommand));
    }
}
