package seedu.address.model.itinerary.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelTestUtil;
import seedu.address.model.itinerary.event.exceptions.ClashingEventException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

/**
 * TODO after all features related to event has been completed
 */
class EventListTest {
    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertThrows(NullPointerException.class, () -> eventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertFalse(eventList.contains(ModelTestUtil.VALID_EVENT_1));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        eventList.add(ModelTestUtil.VALID_EVENT_1);
        assertTrue(eventList.contains(ModelTestUtil.VALID_EVENT_1));
    }


    @Test
    public void add_nullEvent_throwsNullPointerException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertThrows(NullPointerException.class, () -> eventList.add(null));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertThrows(NullPointerException.class, () -> eventList.set(null, ModelTestUtil.VALID_EVENT_1));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertThrows(NullPointerException.class, () -> eventList.set(ModelTestUtil.VALID_EVENT_1, null));
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertThrows(EventNotFoundException.class, () -> eventList.set(ModelTestUtil.VALID_EVENT_1,
                ModelTestUtil.VALID_EVENT_1));
    }

    @Test
    public void setEvent_editedEventIsClashing_throwsClashingEventException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        eventList.add(ModelTestUtil.VALID_EVENT_1);
        assertThrows(ClashingEventException.class, () -> eventList.add(ModelTestUtil.VALID_EVENT_1));
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        eventList.add(ModelTestUtil.VALID_EVENT_1);
        eventList.set(ModelTestUtil.VALID_EVENT_1, ModelTestUtil.VALID_EVENT_3);
        EventList expectedEventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        expectedEventList.add(ModelTestUtil.VALID_EVENT_3);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void remove_personDoesNotExist_throwsEventNotFoundException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertThrows(EventNotFoundException.class, () -> eventList.remove(ModelTestUtil.VALID_EVENT_1));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        eventList.add(ModelTestUtil.VALID_EVENT_1);
        eventList.remove(ModelTestUtil.VALID_EVENT_1);
        EventList expectedeventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertEquals(expectedeventList, eventList);
    }

    @Test
    public void setEvents_eventList_replacesOwnListWithProvidedEventList() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        EventList eventList2 = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));

        eventList.add(ModelTestUtil.VALID_EVENT_1);

        List<Event> expectedEventList = new ArrayList<Event>();
        expectedEventList.add(ModelTestUtil.VALID_EVENT_1);

        eventList2.set(expectedEventList);
        assertEquals(eventList, eventList2);
    }
    //-------------------------------------------------------------------

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertThrows(NullPointerException.class, () -> eventList.set((List<Event>) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() throws ParseException {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        eventList.add(ModelTestUtil.VALID_EVENT_1);
        List<Event> eventListFromOtherList = Collections.singletonList(ModelTestUtil.VALID_EVENT_3);
        eventList.set(eventListFromOtherList);
        EventList expectedeventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0, 0));
        expectedeventList.add(ModelTestUtil.VALID_EVENT_3);
        assertEquals(expectedeventList, eventList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        EventList eventList = new EventList(LocalDateTime.of(2019, 1, 1, 12, 0));
        assertThrows(UnsupportedOperationException.class, () ->
                eventList.asUnmodifiableObservableList().remove(0));
    }

}
