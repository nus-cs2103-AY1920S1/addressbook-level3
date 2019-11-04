package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.util.SampleDataUtil;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code FetchEventCommandTest}.
 */
class FetchEventCommandTest {
    private ReadOnlyAddressBook initialData = SampleDataUtil.getSampleAddressBook();
    private ReadOnlyEventBook initialEventData = SampleDataUtil.getSampleEventBook();
    private Model model = new ModelManager(initialData, initialEventData, new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        FetchEventCommand fetchCommand = new FetchEventCommand(INDEX_FIRST_EVENT);
        Event eventToFetch = initialEventData.getEventList().get(0);
        String expectedMessage = String.format(FetchEventCommand.MESSAGE_SUCCESS, eventToFetch.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());

        assertCommandSuccess(fetchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Integer outOfBoundInteger = initialEventData.getEventList().size() + 1;
        Index invalidIndex = Index.fromOneBased(outOfBoundInteger);
        FetchEventCommand fetchCommand = new FetchEventCommand(invalidIndex);
        assertCommandFailure(fetchCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FetchEventCommand firstCommand = new FetchEventCommand(INDEX_FIRST_EVENT);
        FetchEventCommand secondCommand = new FetchEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        FetchEventCommand firstCommandCopy = new FetchEventCommand(INDEX_FIRST_EVENT);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

}
