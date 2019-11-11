package seedu.address.logic.commands.allocate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.allocate.DeallocateCommand.MESSAGE_FREE_EVENT_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.employee.ClearEmployeesCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeallocateCommandTest}.
 */
class DeallocateCommandTest {
    private ReadOnlyAddressBook initialData = SampleDataUtil.getSampleAddressBook();
    private ReadOnlyEventBook initialEventData = SampleDataUtil.getSampleEventBook();
    private Model model = new ModelManager(initialData, initialEventData, new UserPrefs());

    @Test
    public void execute_onlyIndexSpecifiedUnfilteredList_success() {
        DeallocateCommand deallocateCommand = new DeallocateCommand(INDEX_FIRST_EVENT);
        String employeeNameToDisplay = "ALL Employees";

        String expectedMessage = String.format(MESSAGE_FREE_EVENT_SUCCESS,
                initialEventData.getEventList().get(0).getName(), employeeNameToDisplay);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());

        assertCommandSuccess(deallocateCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Integer outOfBoundInteger = initialEventData.getEventList().size() + 1;
        Index invalidIndex = Index.fromOneBased(outOfBoundInteger);
        DeallocateCommand deallocateCommand = new DeallocateCommand(invalidIndex);

        assertCommandFailure(deallocateCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeallocateCommand standardCommand = new DeallocateCommand(INDEX_FIRST_EVENT);

        // same values -> returns true
        DeallocateCommand commandWithSameValues = new DeallocateCommand(INDEX_FIRST_EVENT);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearEmployeesCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeallocateCommand(INDEX_SECOND_EVENT)));


    }
}
