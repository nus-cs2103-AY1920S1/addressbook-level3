package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.EventUtil.isSameVEvent;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.util.EventUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.model.note.Note;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.testutil.model.ModelStub;

public class EventAddCommandTest {

    private static final String VALID_EVENT_NAME  = "my event";
    private static final String VALID_START_DATE_TIME_STRING = "2019-01-01T03:00";
    private static final String VALID_END_DATE_TIME_STRING = "2019-01-01T04:00";
    private static final String COLOR_NUMBER_STRING = "1";
    private static final String VALID_RECUR_TYPE_STRING = "daily";


    @Test
    public void constructor_nullEventName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddCommand(null, VALID_START_DATE_TIME_STRING,
                VALID_END_DATE_TIME_STRING, VALID_RECUR_TYPE_STRING, COLOR_NUMBER_STRING));
    }

    @Test
    public void constructor_nullStartDateTimeString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddCommand(VALID_EVENT_NAME, null,
                VALID_END_DATE_TIME_STRING, VALID_RECUR_TYPE_STRING, COLOR_NUMBER_STRING));
    }

    @Test
    public void constructor_nullEndDateTimeString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddCommand(VALID_EVENT_NAME, VALID_START_DATE_TIME_STRING,
                null, VALID_RECUR_TYPE_STRING, COLOR_NUMBER_STRING));
    }

    @Test
    public void constructor_nullColorNumberString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddCommand(VALID_EVENT_NAME, VALID_START_DATE_TIME_STRING,
                VALID_END_DATE_TIME_STRING, VALID_RECUR_TYPE_STRING, null));
    }

    @Test
    public void constructor_nullRecurTypeString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddCommand(VALID_EVENT_NAME, VALID_START_DATE_TIME_STRING,
                VALID_END_DATE_TIME_STRING, null, COLOR_NUMBER_STRING));
    }

    @Test
    public void execute_vEventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVEventAdded modelStub = new ModelStubAcceptingVEventAdded();

        CommandResult commandResult = new EventAddCommand(VALID_EVENT_NAME, VALID_START_DATE_TIME_STRING,
                VALID_END_DATE_TIME_STRING, VALID_RECUR_TYPE_STRING, COLOR_NUMBER_STRING).execute(modelStub);
        VEvent expectedAddedVEvent = new VEvent()

        assertEquals(String.format(EventAddCommand.MESSAGE_SUCCESS, validNote), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.notesAdded);
    }
//
//    @Test
//    public void execute_duplicateNote_throwsCommandException() {
//        Note validNote = new NoteBuilder().build();
//        NoteAddCommand addCommand = new NoteAddCommand(validNote);
//        ModelStub modelStub = new ModelStubWithNote(validNote);
//        assertThrows(CommandException.class, () -> addCommand.execute(modelStub), MESSAGE_DUPLICATE_NOTE);
//    }
//
//    @Test
//    public void equals() {
//        Note note = new NoteBuilder().withNote("Note").build();
//        Note otherNote = new NoteBuilder().withNote("Other Note").build();
//        NoteAddCommand addNoteCommand = new NoteAddCommand(note);
//        NoteAddCommand addOtherNoteCommand = new NoteAddCommand(otherNote);
//
//        // same object -> returns true
//        assertTrue(addNoteCommand.equals(addNoteCommand));
//
//        // same values -> returns true
//        NoteAddCommand addNoteCommandCopy = new NoteAddCommand(note);
//        assertTrue(addNoteCommand.equals(addNoteCommandCopy));
//
//        // different types -> returns false
//        assertFalse(addNoteCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(addNoteCommand.equals(null));
//
//        // different note -> returns false
//        assertFalse(addNoteCommand.equals(addOtherNoteCommand));
//    }

//    /**
//     * A Model stub that contains a single note.
//     */
//    private class ModelStubWithNote extends ModelStub {
//        private final Note note;
//
//        ModelStubWithNote(Note note) {
//            requireNonNull(note);
//            this.note = note;
//        }
//
//        @Override
//        public boolean hasNote(Note note) {
//            requireNonNull(note);
//            return this.note.isSameNote(note);
//        }
//    }

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
