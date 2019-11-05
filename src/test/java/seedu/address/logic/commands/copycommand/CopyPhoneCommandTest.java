package seedu.address.logic.commands.copycommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPhoneAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PHONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PHONE;
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
import seedu.address.model.phone.Phone;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CopyPhoneCommand}.
 */
public class CopyPhoneCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success()
            throws IOException, UnsupportedFlavorException, CommandException {
        Phone phoneToCopy = model.getFilteredPhoneList().get(INDEX_FIRST_PHONE.getZeroBased());
        CopyPhoneCommand copyCommand = new CopyPhoneCommand(INDEX_FIRST_PHONE);

        String expectedMessage = String.format(CopyPhoneCommand.MESSAGE_COPY_PHONE_SUCCESS, phoneToCopy);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        copyCommand.execute(model, new CommandHistory(), new UndoRedoStack());

        String data = (String) Toolkit.getDefaultToolkit()
                .getSystemClipboard().getData(DataFlavor.stringFlavor);

        assertEquals(data, phoneToCopy.toString());
        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPhoneList().size() + 1);
        CopyPhoneCommand copyCommand = new CopyPhoneCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPhoneAtIndex(model, INDEX_FIRST_PHONE);

        Phone phoneToCopy = model.getFilteredPhoneList().get(INDEX_FIRST_PHONE.getZeroBased());
        CopyPhoneCommand copyCommand = new CopyPhoneCommand(INDEX_FIRST_PHONE);

        String expectedMessage = String.format(CopyPhoneCommand.MESSAGE_COPY_PHONE_SUCCESS, phoneToCopy);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        showPhoneAtIndex(expectedModel, INDEX_FIRST_PHONE);
        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPhoneAtIndex(model, INDEX_FIRST_PHONE);

        Index outOfBoundIndex = INDEX_SECOND_PHONE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPhoneBook().getList().size());

        CopyPhoneCommand copyCommand = new CopyPhoneCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CopyPhoneCommand copyFirstCommand = new CopyPhoneCommand(INDEX_FIRST_PHONE);
        CopyPhoneCommand copySecondCommand = new CopyPhoneCommand(INDEX_SECOND_PHONE);

        // same object -> returns true
        assertTrue(copyFirstCommand.equals(copyFirstCommand));

        // same values -> returns true
        CopyPhoneCommand copyFirstCommandCopy = new CopyPhoneCommand(INDEX_FIRST_PHONE);
        assertTrue(copyFirstCommand.equals(copyFirstCommandCopy));

        // different types -> returns false
        assertFalse(copyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(copyFirstCommand.equals(null));

        // different phone -> returns false
        assertFalse(copyFirstCommand.equals(copySecondCommand));
    }
}
