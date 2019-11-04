package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.event.TypicalEvents.EVENT1;
import static seedu.address.testutil.event.TypicalEvents.EVENT2;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEventsRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.model.event.exceptions.DuplicateVEventException;
import seedu.address.testutil.event.EventBuilder;

public class EventRecordTest {
    private final EventRecord eventRecord = new EventRecord();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventRecord.getVEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.resetData(null));
    }

    @Test
    public void resetData_withValidEventRecord_replacesData() {
        EventRecord newData = getTypicalEventsRecord();
        eventRecord.resetData(newData);
        assertEquals(newData, eventRecord);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsDuplicatePersonException() {
        // Two events with the same name, start date time and end date time
        Event editedEvent1 = new EventBuilder(EVENT1).withEventName(EVENT2.getEventName())
                .withStartDateTime(EVENT2.getStartDateTime())
                .withEndDateTime(EVENT2.getEndDateTime())
                .build();
        List<Event> newEvents = Arrays.asList(editedEvent1, EVENT2);
        EventRecordStub newData = new EventRecordStub(newEvents);

        assertThrows(DuplicateVEventException.class, () -> eventRecord.resetData(newData));
    }

    @Test
    public void hasVEvent_nullVEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.contains(null));
    }
//
//    @Test
//    public void hasPerson_personNotInAddressBook_returnsFalse() {
//        assertFalse(addressBook.hasPerson(ALICE));
//    }
//
//    @Test
//    public void hasPerson_personInAddressBook_returnsTrue() {
//        addressBook.addPerson(ALICE);
//        assertTrue(addressBook.hasPerson(ALICE));
//    }
//
//    @Test
//    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
//        addressBook.addPerson(ALICE);
//        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                .build();
//        assertTrue(addressBook.hasPerson(editedAlice));
//    }
//
//    @Test
//    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
//    }
//
    /**
     * A stub ReadOnlyEvents whose events list can violate interface constraints.
     */
    private static class EventRecordStub implements ReadOnlyEvents {
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        EventRecordStub(Collection<Event> events) {
            this.events.setAll(events);
        }

        @Override
        public ObservableList<Event> getAllEvents() {
            return events;
        }
    }
}
