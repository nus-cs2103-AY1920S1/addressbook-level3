package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalEventDates.OCT_20_2019;
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

class DeleteDateMappingCommandTest {
    private ReadOnlyAddressBook initialData = SampleDataUtil.getSampleAddressBook();
    private ReadOnlyEventBook initialEventData = SampleDataUtil.getSampleEventBook();
    private Model model = new ModelManager(initialData, initialEventData, new UserPrefs());

    @Test
    void execute_validIndex_success() {
        DeleteDateMappingCommand deleteDateMappingCommand =
                new DeleteDateMappingCommand(INDEX_FIRST_EVENT, OCT_20_2019);
        Event eventToDeleteDateMapping = initialEventData.getEventList().get(0);
        String expectedMessage = String.format(DeleteDateMappingCommand.MESSAGE_CLEAR_EVENT_DATE_MAPPING_SUCCESS,
                OCT_20_2019, eventToDeleteDateMapping.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());
        //assertCommandSuccess(deleteDateMappingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_invalidIndex_failure() {
        Integer outOfBoundInteger = initialEventData.getEventList().size() + 1;
        Index invalidIndex = Index.fromOneBased(outOfBoundInteger);
        DeleteDateMappingCommand deleteDateMappingCommand = new DeleteDateMappingCommand(invalidIndex, OCT_20_2019);
        assertCommandFailure(deleteDateMappingCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    void equals() {
        ClearDateMappingCommand firstCommand = new ClearDateMappingCommand(INDEX_FIRST_EVENT);
        ClearDateMappingCommand secondCommand = new ClearDateMappingCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        ClearDateMappingCommand firstCommandCopy = new ClearDateMappingCommand(INDEX_FIRST_EVENT);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different index -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

}
