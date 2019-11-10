package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

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
 * {@code ListEventCommandTest}.
 */
class ListEventCommandTest {
    private ReadOnlyAddressBook initialData = SampleDataUtil.getSampleAddressBook();
    private ReadOnlyEventBook initialEventData = SampleDataUtil.getSampleEventBook();
    private Model model = new ModelManager(initialData, initialEventData, new UserPrefs());

    @Test
    void execute() {
        ListEventCommand listEventCommand = new ListEventCommand();
        String expectedMessage = ListEventCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());

        assertCommandSuccess(listEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void equals() {
        ListEventCommand firstCommand = new ListEventCommand();
        ListEventCommand secondCommand = new ListEventCommand();

        // same object -> returns true. There are no 'variants' of List Commands
        assertTrue(firstCommand.equals(firstCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

    }
}
