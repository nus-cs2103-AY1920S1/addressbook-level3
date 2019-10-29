package seedu.address.logic.parser.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.FetchEventCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.util.SampleDataUtil;

class FetchEventCommandParserTest {
    private ReadOnlyAddressBook initialData = SampleDataUtil.getSampleAddressBook();
    private ReadOnlyEventBook initialEventData = SampleDataUtil.getSampleEventBook();
    private Model model = new ModelManager(initialData, initialEventData, new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToFetch = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        FetchEventCommand fetchCommand = new FetchEventCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(FetchEventCommand.MESSAGE_SUCCESS, eventToFetch);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
                model.getEventBook(), new UserPrefs());

        assertCommandSuccess(fetchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        FetchEventCommand fetchCommand = new FetchEventCommand(outOfBoundIndex);
        assertCommandFailure(fetchCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Event eventToFetch = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        FetchEventCommand fetchCommand = new FetchEventCommand(INDEX_FIRST_EVENT);
        String expectedMessage = String.format(FetchEventCommand.MESSAGE_SUCCESS, eventToFetch);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());

        assertCommandSuccess(fetchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventBook().getEventList().size());

        FetchEventCommand fetchCommand = new FetchEventCommand(outOfBoundIndex);

        assertCommandFailure(fetchCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FetchEventCommand fetchFirstCommand = new FetchEventCommand(INDEX_FIRST_EVENT);
        FetchEventCommand fetchSecondCommand = new FetchEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(fetchFirstCommand.equals(fetchFirstCommand));

        // same values -> returns true
        FetchEventCommand fetchFirstCommandCopy = new FetchEventCommand(INDEX_FIRST_EVENT);
        assertTrue(fetchFirstCommand.equals(fetchFirstCommandCopy));

        // different types -> returns false
        assertFalse(fetchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(fetchFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(fetchFirstCommand.equals(fetchSecondCommand));
    }

}
