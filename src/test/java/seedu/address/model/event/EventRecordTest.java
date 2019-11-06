package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.event.TypicalEvents.EVENT1;
import static seedu.address.testutil.event.TypicalEvents.EVENT2;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEvents;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEventsRecord;
import static seedu.address.testutil.event.TypicalVEvents.VEVENT1;
import static seedu.address.testutil.event.TypicalVEvents.VEVENT2;
import static seedu.address.testutil.event.TypicalVEvents.VEVENT3;
import static seedu.address.testutil.event.TypicalVEvents.VEVENT4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.model.event.exceptions.DuplicateVEventException;
import seedu.address.model.event.exceptions.VEventNotFoundException;
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
    public void resetData_withDuplicateEvents_throwsDuplicateVEventException() {
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
    public void resetDataWithEventList_withValidEventRecord_replacesData() {
        List<Event> newData = getTypicalEvents();
        eventRecord.resetDataWithEventList(newData);
        EventRecord expectedEventRecord = new EventRecord(getTypicalEventsRecord());
        assertEquals(eventRecord, expectedEventRecord);
    }

    @Test
    public void resetDataWithEventList_withDuplicateEvents_throwsDuplicateVEventException() {
        // Two events with the same name, start date time and end date time
        Event editedEvent1 = new EventBuilder(EVENT1).withEventName(EVENT2.getEventName())
                .withStartDateTime(EVENT2.getStartDateTime())
                .withEndDateTime(EVENT2.getEndDateTime())
                .build();
        List<Event> newEvents = Arrays.asList(editedEvent1, EVENT2);

        assertThrows(DuplicateVEventException.class, () -> eventRecord.resetDataWithEventList(newEvents));
    }

    @Test
    public void contains_nullVEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.contains(null));
    }

    @Test
    public void contains_vEventNotInEventRecord_returnsFalse() {
        assertFalse(eventRecord.contains(VEVENT1));
    }

    @Test
    public void contains_vEventInEventRecord_returnsFalse() {
        eventRecord.addVEvent(VEVENT1);
        assertTrue(eventRecord.contains(VEVENT1));
    }

    @Test
    public void hasVEvent_vEventWithSameIdentityFieldsInEventRecord_returnsTrue() {
        eventRecord.addVEvent(VEVENT1);
        VEvent editedVEvent1 = new VEvent(VEVENT1).withCategories("group23")
                .withRecurrenceRule("FREQ=DAILY;COUNT=2");
        assertTrue(eventRecord.contains(editedVEvent1));
    }

    @Test
    public void getVEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eventRecord.getVEventList().remove(0));
    }

    @Test
    public void add_nullVEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.addVEvent(null));
    }

    @Test
    public void add_duplicateVEvent_throwsDuplicateVEventException() {
        eventRecord.addVEvent(VEVENT1);
        assertThrows(DuplicateVEventException.class, () -> eventRecord.addVEvent(VEVENT1));
    }

    @Test
    public void setVEvent_nullTargetVEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.setVEvent(null, VEVENT1));
    }

    @Test
    public void setVEvent_editedVEventIsSameVEvent_success() {
        eventRecord.addVEvent(VEVENT1);
        eventRecord.setVEvent(Index.fromZeroBased(0), VEVENT1);
        EventRecord expectedUniqueEventRecord = new EventRecord();
        expectedUniqueEventRecord.addVEvent(VEVENT1);
        assertEquals(eventRecord, expectedUniqueEventRecord);
    }

    @Test
    public void setVEvent_editedVEventHasSameIdentity_success() {
        eventRecord.addVEvent(VEVENT1);
        VEvent editedVEvent1 = new VEvent(VEVENT1).withCategories("group23")
                .withRecurrenceRule("FREQ=DAILY;COUNT=2");
        eventRecord.setVEvent(Index.fromZeroBased(0), editedVEvent1);
        EventRecord expectedUniqueEventRecord = new EventRecord();
        expectedUniqueEventRecord.addVEvent(editedVEvent1);
        assertEquals(expectedUniqueEventRecord, eventRecord);
    }

    @Test
    public void setVEvent_editedVEventHasDifferentIdentity_success() {
        eventRecord.addVEvent(VEVENT1);
        eventRecord.setVEvent(Index.fromZeroBased(0), VEVENT2);
        EventRecord expectedUniqueEventRecord = new EventRecord();
        expectedUniqueEventRecord.addVEvent(VEVENT2);
        assertEquals(expectedUniqueEventRecord, eventRecord);
    }

    @Test
    public void setVEvent_editedVEventHasNonUniqueIdentity_throwsDuplicateVEventException() {
        eventRecord.addVEvent(VEVENT1);
        eventRecord.addVEvent(VEVENT2);
        // attempt to set VEVENT1 to become VEVENT2 when eventRecord contains VEVENT2
        assertThrows(DuplicateVEventException.class, () -> eventRecord.setVEvent(Index.fromZeroBased(0), VEVENT2));
    }

    @Test
    public void deleteVEvent_nullVEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.deleteVEvent(null));
    }

    @Test
    public void deleteVEvent_indexDoesNotExist_throwsVEventNotFoundException() {
        assertThrows(IndexOutOfBoundsException.class, () -> eventRecord.deleteVEvent(Index.fromZeroBased(0)));
    }

    @Test
    public void deleteVEvent_existingVEvent_removesVEvent() {
        eventRecord.addVEvent(VEVENT1);
        eventRecord.deleteVEvent(Index.fromZeroBased(0));
        EventRecord expectedUniqueEventRecord = new EventRecord();
        assertEquals(expectedUniqueEventRecord, eventRecord);
    }

    @Test
    public void setVEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.setVEvents((List<VEvent>) null));
    }

    @Test
    public void setVEvents_list_replacesOwnListWithProvidedList() {
        eventRecord.addVEvent(VEVENT1);
        List<VEvent> vEventList = Collections.singletonList(VEVENT2);
        eventRecord.setVEvents(vEventList);
        EventRecord expectedUniqueVEventList = new EventRecord();
        expectedUniqueVEventList.addVEvent(VEVENT2);
        assertEquals(expectedUniqueVEventList, eventRecord);
    }

    @Test
    public void setVEvents_listWithDuplicateVEvents_throwsDuplicateVEventException() {
        List<VEvent> listWithDuplicateVEvents = Arrays.asList(VEVENT1, VEVENT1);
        assertThrows(DuplicateVEventException.class, () -> eventRecord.setVEvents(listWithDuplicateVEvents));
    }

    @Test
    public void getVEvent_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.getVEvent(null));
    }

    @Test
    public void getVEvent_withValidIndex_returnsTrue() {
        eventRecord.addVEvent(VEVENT1);
        VEvent vEvent = eventRecord.getVEvent(Index.fromZeroBased(0));
        assertEquals(VEVENT1, vEvent);
    }

    @Test
    public void getVEvent_withInvalidIndex_throwsIndexOutOfBoundException() {
        assertThrows(IndexOutOfBoundsException.class, () -> eventRecord.getVEvent(Index.fromZeroBased(0)));
    }

    @Test
    public void findVEvents_nullDesiredEventName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventRecord.findVEvents(null));
    }

    @Test
    public void findVEvents_withValidDesiredEventName_returnsTrue() {
        eventRecord.addVEvent(VEVENT1);
        eventRecord.addVEvent(VEVENT2);
        List<Pair<Index, VEvent>> expectedResultList = new ArrayList<>();
        expectedResultList.add(new Pair(Index.fromZeroBased(0), VEVENT1));
        List<Pair<Index, VEvent>> actualResultList = eventRecord.findVEvents(VEVENT1.getSummary().getValue());
        assertEquals(expectedResultList, actualResultList);
    }

    @Test
    public void findVEvents_withDesiredEventNameNotFound_returnsEmptyList() {
        eventRecord.addVEvent(VEVENT1);
        eventRecord.addVEvent(VEVENT2);
        List<Pair<Index, VEvent>> actualResultList = eventRecord.findVEvents(VEVENT3.getSummary().getValue());
        assertEquals(Collections.emptyList(), actualResultList);
    }

    @Test
    public void findMostSimilarVEvent_emptyEventRecord_throwsVEventNotFoundException() {
        assertThrows(VEventNotFoundException.class, () -> eventRecord.findMostSimilarVEvent("event name"));
    }

    @Test
    public void findMostSimilarVEvent_nullDesiredEventName_throwsVEventNotFoundException() {
        assertThrows(NullPointerException.class, () -> eventRecord.findMostSimilarVEvent(null));
    }

    @Test
    public void findMostSimilarVEvent_exactEventName_returnsExactVEvent() {
        eventRecord.addVEvent(VEVENT1);
        Pair<Index, VEvent> vEventPairFound = eventRecord.findMostSimilarVEvent(VEVENT1.getSummary().getValue());
        assertEquals(vEventPairFound.getValue(), VEVENT1);
    }

    @Test
    public void findMostSimilarVEvent_similarEventName_returnsCorrectVEventAndIndex() {
        eventRecord.addVEvent(VEVENT2);
        eventRecord.addVEvent(VEVENT3);
        eventRecord.addVEvent(VEVENT4);
        Index desiredIndex = Index.fromZeroBased(0);
        eventRecord.setVEvent(desiredIndex, VEVENT1);
        Pair<Index, VEvent> vEventPairFound =
                eventRecord.findMostSimilarVEvent(VEVENT1.getSummary().getValue().substring(0, 5));
        assertEquals(desiredIndex, vEventPairFound.getKey());
        assertEquals(VEVENT1, vEventPairFound.getValue());
    }

    @Test
    public void findMostSimilarVEvent_irrelevantEventName_returnsMostSimilarVEvent() {
        eventRecord.addVEvent(VEVENT2);
        Pair<Index, VEvent> vEventPairFound =
                eventRecord.findMostSimilarVEvent("irrelevant event name");
        assertEquals(VEVENT2, vEventPairFound.getValue());
    }

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
