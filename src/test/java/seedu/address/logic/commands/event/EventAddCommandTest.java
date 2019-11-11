package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_EVENT;
import static seedu.address.commons.util.EventUtil.isSameVEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.testutil.model.ModelStub;

/**
 * Event add command test.
 */
public class EventAddCommandTest {

    private static final String VALID_EVENT_NAME = "my event";
    private static final String VALID_OTHER_EVENT_NAME = "my other event";
    private static final LocalDateTime VALID_DATE_TIME_START = LocalDateTime.parse("2019-01-01T03:00");
    private static final LocalDateTime VALID_DATE_TIME_END = LocalDateTime.parse("2019-01-01T04:00");

    @Test
    public void constructor_nullVEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddCommand(null));
    }

    @Test
    public void execute_noteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVEventAdded modelStub = new ModelStubAcceptingVEventAdded();
        VEvent validVEvent = new VEvent().withSummary(VALID_EVENT_NAME);

        CommandResult commandResult = new EventAddCommand(validVEvent).execute(modelStub);

        assertEquals(String.format(EventAddCommand.MESSAGE_SUCCESS, validVEvent.getSummary().getValue()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVEvent), modelStub.vEventsAdded);
    }

    @Test
    public void execute_duplicateVEvent_throwsCommandException() {
        VEvent validVEvent = new VEvent().withSummary(VALID_EVENT_NAME).withDateTimeStart(VALID_DATE_TIME_START)
                .withDateTimeEnd(VALID_DATE_TIME_END);
        EventAddCommand addCommand = new EventAddCommand(validVEvent);
        ModelStub modelStub = new ModelStubWithVEvent(validVEvent);
        assertThrows(CommandException.class, () -> addCommand.execute(modelStub), MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void equals() {
        VEvent vEvent = new VEvent().withSummary(VALID_EVENT_NAME);
        VEvent otherVEvent = new VEvent().withSummary(VALID_OTHER_EVENT_NAME);
        EventAddCommand addEventCommand = new EventAddCommand(vEvent);
        EventAddCommand addOtherEventCommand = new EventAddCommand(otherVEvent);

        // same object -> returns true
        assertTrue(addEventCommand.equals(addEventCommand));

        // same values -> returns true
        EventAddCommand addVEventCommandCopy = new EventAddCommand(vEvent);
        assertTrue(addEventCommand.equals(addVEventCommandCopy));

        // different types -> returns false
        assertFalse(addEventCommand.equals(1));

        // null -> returns false
        assertFalse(addEventCommand.equals(null));

        // different note -> returns false
        assertFalse(addEventCommand.equals(addOtherEventCommand));
    }

    /**
     * A Model stub that contains a single vEvent.
     */
    private class ModelStubWithVEvent extends ModelStub {
        private final VEvent vEvent;

        ModelStubWithVEvent(VEvent vEvent) {
            requireNonNull(vEvent);
            this.vEvent = vEvent;
        }

        @Override
        public boolean hasVEvent(VEvent otherVEvent) {
            requireNonNull(otherVEvent);
            return isSameVEvent(this.vEvent, otherVEvent);
        }
    }

    /**
     * A Model stub that always accept the VEvent being added.
     */
    private class ModelStubAcceptingVEventAdded extends ModelStub {
        final ArrayList<VEvent> vEventsAdded = new ArrayList<>();

        @Override
        public boolean hasVEvent(VEvent vEvent) {
            requireNonNull(vEvent);
            return vEventsAdded.stream().anyMatch(streamedEvent -> isSameVEvent(streamedEvent, vEvent));
        }

        @Override
        public void addVEvent(VEvent vEvent) {
            requireNonNull(vEvent);
            vEventsAdded.add(vEvent);
        }

        @Override
        public ReadOnlyVEvents getVEventRecord() {
            return new EventRecord();
        }
    }
}
