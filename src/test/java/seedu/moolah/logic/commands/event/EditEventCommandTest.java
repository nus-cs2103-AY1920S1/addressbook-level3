package seedu.moolah.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.logic.commands.CommandTestUtil.DESC_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_CATEGORY_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_TIMESTAMP_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.moolah.logic.commands.general.ClearCommand;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;
import seedu.moolah.testutil.EditEventDescriptorBuilder;
import seedu.moolah.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditEventCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_allFieldsSpecifiedUnfilteredList_success() {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand command = new EditEventCommand(INDEX_FIRST, descriptor);

        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()));

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        Event lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList
                .withDescription(VALID_EVENT_DESCRIPTION_BUFFET)
                .withPrice(VALID_EVENT_PRICE_BUFFET)
                .withCategory(VALID_EVENT_CATEGORY_BUFFET)
                .withTimestamp(VALID_EVENT_TIMESTAMP_BIRTHDAY).build();

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_EVENT_DESCRIPTION_BUFFET)
                .withPrice(VALID_EVENT_PRICE_BUFFET)
                .withCategory(VALID_EVENT_CATEGORY_BUFFET)
                .withTimestamp(VALID_EVENT_TIMESTAMP_BIRTHDAY).build();
        EditEventCommand command = new EditEventCommand(indexLastEvent, descriptor);

        expectedModel.setEvent(lastEvent, editedEvent);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setMooLah(model.getMooLah()));

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand command = new EditEventCommand(INDEX_FIRST, new EditEventDescriptor());
        Event editedEvent = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());

        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()));

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    //    @Test
    //    public void run_filteredList_success() {
    //        showEventAtIndex(model, INDEX_FIRST_EVENT);
    //
    //        Event eventInFilteredList = model
    //                .getFilteredEventList()
    //                .get(INDEX_FIRST_EVENT.getZeroBased());
    //        Event editedEvent = new EventBuilder(eventInFilteredList)
    //                .withDescription(VALID_DESCRIPTION_TRANSPORT).build();
    //        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT,
    //                new EditEventDescriptorBuilder()
    //                        .withDescription(VALID_DESCRIPTION_TRANSPORT).build());
    //
    //        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);
    //
    //        Model expectedModel = new ModelManager(new MooLah(model.getMooLah()),
    //                new UserPrefs(), new ModelHistory());
    //        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);
    //        expectedModel.setModelHistory(new ModelHistory("", makeModelStack(model), makeModelStack()));
    //
    //        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    //    }

    // Editing an event to have the same details as another should not result in failure

    @Test
    public void run_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_EVENT_DESCRIPTION_BUFFET).build();
        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    //    /**
    //     * Edit filtered list where index is larger than size of filtered list,
    //     * but smaller than size of address book
    //     */
    //    @Test
    //    public void run_invalidEventIndexFilteredList_failure() {
    //        showEventAtIndex(model, INDEX_FIRST_EVENT);
    //        Index outOfBoundIndex = INDEX_SECOND_EVENT;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getMooLah().getEventList().size());
    //
    //        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex,
    //                new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_TRANSPORT).build());
    //
    //        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST, DESC_BUFFET);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_BUFFET);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditEventCommand(INDEX_SECOND, DESC_BUFFET));
    }

}
