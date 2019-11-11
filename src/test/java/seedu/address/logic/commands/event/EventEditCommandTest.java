package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_EVENT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.EventUtil.vEventToString;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.event.EventEditCommand.MESSAGE_EDIT_EVENT_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THREE;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWO;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEventsRecord;
import static seedu.address.testutil.event.TypicalVEvents.NOT_IN_TYPICAL_VEVENT;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.event.EventEditCommand.EditVEventDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Tests for event edit command.
 * Contains integration testing due to interactions between model manager and EditCommand.
 */
public class EventEditCommandTest {

    private static final String EDITED_EVENT_NAME = "Edited event name";
    private static final LocalDateTime EDITED_DATE_TIME_START = LocalDateTime.parse("2019-04-05T03:00");
    private static final LocalDateTime EDITED_DATE_TIME_END = LocalDateTime.parse("2019-04-05T04:00");

    private Model model = new ModelManager();

    public EventEditCommandTest() {
        model.setEventRecord(getTypicalEventsRecord());
    }

    @Test
    public void execute_editAllFields_success() {
        VEvent editedVEvent = NOT_IN_TYPICAL_VEVENT;
        EditVEventDescriptor descriptor = new EditVEventDescriptor();
        descriptor.setEventName(editedVEvent.getSummary().getValue());
        descriptor.setStartDateTime(LocalDateTime.from(editedVEvent.getDateTimeStart().getValue()));
        descriptor.setEndDateTime(LocalDateTime.from(editedVEvent.getDateTimeEnd().getValue()));
        descriptor.setColorCategory(editedVEvent.getCategories());
        descriptor.setRecurrenceRule(editedVEvent.getRecurrenceRule());

        EventEditCommand editCommand = new EventEditCommand(INDEX_ONE, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_EVENT_SUCCESS, vEventToString(editedVEvent));
        Model expectedModel = new ModelManager();
        expectedModel.setEventRecord(getTypicalEventsRecord());
        expectedModel.setVEvent(INDEX_ONE, editedVEvent);

        assertCommandSuccess(editCommand, model, new CommandResult(expectedMessage, CommandResultType.SHOW_SCHEDULE),
                expectedModel);
    }

    @Test
    public void execute_editSomeFields_success() {
        Index indexLastVEvent = Index.fromOneBased(model.getVEventList().size());
        VEvent lastVEvent = model.getVEvent(indexLastVEvent);

        VEvent editedVEvent = lastVEvent.withSummary(EDITED_EVENT_NAME).withDateTimeStart(EDITED_DATE_TIME_START)
                .withDateTimeEnd(EDITED_DATE_TIME_END);

        EditVEventDescriptor descriptor = new EditVEventDescriptor();
        descriptor.setStartDateTime(LocalDateTime.from(editedVEvent.getDateTimeStart().getValue()));
        descriptor.setEndDateTime(LocalDateTime.from(editedVEvent.getDateTimeEnd().getValue()));

        EventEditCommand editCommand = new EventEditCommand(indexLastVEvent, descriptor);
        String expectedMessage = String.format(MESSAGE_EDIT_EVENT_SUCCESS, vEventToString(editedVEvent));

        Model expectedModel = new ModelManager();
        expectedModel.setEventRecord(getTypicalEventsRecord());
        expectedModel.setVEvent(indexLastVEvent, editedVEvent);

        assertCommandSuccess(editCommand, model, new CommandResult(expectedMessage, CommandResultType.SHOW_SCHEDULE),
                expectedModel);
    }

    @Test
    public void execute_editNoFields_throwsCommandException() {
        EventEditCommand editCommand = new EventEditCommand(INDEX_ONE, new EditVEventDescriptor());
        assertThrows(CommandException.class, () -> editCommand.execute(model), EventEditCommand.NO_FIELDS_CHANGED);
    }

    @Test
    public void execute_duplicateNewVEvent_throwsCommandException() {
        VEvent vEventInList = model.getVEvent(INDEX_TWO);
        EditVEventDescriptor descriptor = new EditVEventDescriptor();
        descriptor.setEventName(vEventInList.getSummary().getValue());
        descriptor.setStartDateTime(LocalDateTime.from(vEventInList.getDateTimeStart().getValue()));
        descriptor.setEndDateTime(LocalDateTime.from(vEventInList.getDateTimeEnd().getValue()));
        EventEditCommand editCommand = new EventEditCommand(INDEX_ONE, descriptor);
        assertThrows(CommandException.class, () -> editCommand.execute(model), MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidNoteIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getVEventList().size() + 1);
        EditVEventDescriptor descriptor = new EditVEventDescriptor();
        descriptor.setEventName(EDITED_EVENT_NAME);
        EventEditCommand editCommand = new EventEditCommand(outOfBoundIndex, descriptor);
        assertThrows(CommandException.class, () -> editCommand.execute(model), MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditVEventDescriptor standardDescriptor = new EditVEventDescriptor();
        standardDescriptor.setEventName(EDITED_EVENT_NAME);
        final EventEditCommand standardCommand = new EventEditCommand(INDEX_ONE, standardDescriptor);

        final EditVEventDescriptor standardDescriptorCopy = new EditVEventDescriptor();
        standardDescriptorCopy.setEventName(EDITED_EVENT_NAME);
        EventEditCommand standardCommandCopy = new EventEditCommand(INDEX_ONE, standardDescriptorCopy);

        //same content -> returns true
        assertTrue(standardCommand.equals(standardCommandCopy));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EventEditCommand(INDEX_THREE, standardDescriptor)));

        // different descriptor -> returns false
        final EditVEventDescriptor differentDescriptor = new EditVEventDescriptor();
        differentDescriptor.setEventName("Another event name");
        assertFalse(standardCommand.equals(new EventEditCommand(INDEX_ONE, differentDescriptor)));
    }
}
