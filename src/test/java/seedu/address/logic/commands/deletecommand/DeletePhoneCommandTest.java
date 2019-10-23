package seedu.address.logic.commands.deletecommand;

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

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePhoneCommand}.
 */
public class DeletePhoneCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Phone phoneToDelete = model.getFilteredPhoneList().get(INDEX_FIRST_PHONE.getZeroBased());
        DeletePhoneCommand deleteCommand = new DeletePhoneCommand(INDEX_FIRST_PHONE);

        String expectedMessage = String.format(DeletePhoneCommand.MESSAGE_DELETE_PHONE_SUCCESS, phoneToDelete);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.deletePhone(phoneToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPhoneList().size() + 1);
        DeletePhoneCommand deleteCommand = new DeletePhoneCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPhoneAtIndex(model, INDEX_FIRST_PHONE);

        Phone phoneToDelete = model.getFilteredPhoneList().get(INDEX_FIRST_PHONE.getZeroBased());
        DeletePhoneCommand deleteCommand = new DeletePhoneCommand(INDEX_FIRST_PHONE);

        String expectedMessage = String.format(DeletePhoneCommand.MESSAGE_DELETE_PHONE_SUCCESS, phoneToDelete);

        Model expectedModel = new ModelManager(getTypicalCustomerBook(), new DataBook<Phone>(model.getPhoneBook()),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.deletePhone(phoneToDelete);
        showNoPhone(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPhoneAtIndex(model, INDEX_FIRST_PHONE);

        Index outOfBoundIndex = INDEX_SECOND_PHONE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPhoneBook().getList().size());

        DeletePhoneCommand deleteCommand = new DeletePhoneCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePhoneCommand deleteFirstCommand = new DeletePhoneCommand(INDEX_FIRST_PHONE);
        DeletePhoneCommand deleteSecondCommand = new DeletePhoneCommand(INDEX_SECOND_PHONE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePhoneCommand deleteFirstCommandCopy = new DeletePhoneCommand(INDEX_FIRST_PHONE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different phone -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPhone(Model model) {
        model.updateFilteredPhoneList(p -> false);

        assertTrue(model.getFilteredPhoneList().isEmpty());
    }
}
