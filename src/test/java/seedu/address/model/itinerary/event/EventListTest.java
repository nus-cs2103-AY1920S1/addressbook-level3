package seedu.address.model.itinerary.event;

import org.junit.jupiter.api.Test;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.event.exceptions.ClashingEventException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.model.ModelTestUtil.VALID_EVENT_1;
import static seedu.address.model.ModelTestUtil.VALID_EVENT_2;
import static seedu.address.testutil.TypicalPersons.BOB;

/**
 * TODO after all features related to event has been completed
 */
class EventListTest {
//    public static final EventList eventList = new EventList();
//
//    @Test
//    public void contains_nullEvent_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> eventList.contains(null));
//    }
//
//    @Test
//    public void contains_personNotInList_returnsFalse() {
//        assertFalse(eventList.contains(VALID_EVENT_1));
//    }
//
//    @Test
//    public void contains_personInList_returnsTrue() {
//        eventList.add(VALID_EVENT_1);
//        assertTrue(eventList.contains(VALID_EVENT_1));
//    }
//
//
//    @Test
//    public void add_nullEvent_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> eventList.add(null));
//    }
//
//    @Test
//    public void setEvent_nullTargetEvent_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> eventList.set(null, VALID_EVENT_1));
//    }
//
//    @Test
//    public void setEvent_nullEditedEvent_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> eventList.set(VALID_EVENT_1, null));
//    }
//
//    @Test
//    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
//        assertThrows(EventNotFoundException.class, () -> eventList.set(VALID_EVENT_1, VALID_EVENT_1));
//    }
//
//    @Test
//    public void setEvent_editedEventIsClashing_throwsClashingEventException() {
//        eventList.add(VALID_EVENT_1);
//        assertThrows(ClashingEventException.class, () -> eventList.add(VALID_EVENT_1));
//    }
//
//    @Test
//    public void setEvent_editedEventHasDifferentIdentity_success() {
//        eventList.add(VALID_EVENT_1);
//        eventList.set(VALID_EVENT_1, VALID_EVENT_2);
//        EventList expectedeventList = new EventList();
//        expectedeventList.add(VALID_EVENT_2);
//        assertEquals(expectedeventList, eventList);
//    }
//
//    @Test
//    public void remove_personDoesNotExist_throwsEventNotFoundException() {
//        assertThrows(EventNotFoundException.class, () -> eventList.remove(VALID_EVENT_1));
//    }
//
//    @Test
//    public void remove_existingEvent_removesEvent() {
//        eventList.add(VALID_EVENT_1);
//        eventList.remove(VALID_EVENT_1);
//        EventList expectedeventList = new EventList();
//        assertEquals(expectedeventList, eventList);
//    }
//
//    @Test
//    public void setEvents_eventList_replacesOwnListWithProvidedeventList() {
//        eventList.add(VALID_EVENT_1);
//        List<Event> expectedeventList = new ArrayList<Event>();
//        expectedeventList.add(VALID_EVENT_2);
//        eventList.set(expectedeventList);
//        assertEquals(expectedeventList, eventList);
//    }
//    //-------------------------------------------------------------------
//
//    @Test
//    public void setEvents_nullList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> eventList.set((List<Event>) null));
//    }
//
//    @Test
//    public void setEvents_list_replacesOwnListWithProvidedList() {
//        eventList.add(VALID_EVENT_1);
//        List<Event> personList = Collections.singletonList(VALID_EVENT_2);
//        eventList.set(personList);
//        EventList expectedeventList = new EventList();
//        expectedeventList.add(VALID_EVENT_2);
//        assertEquals(expectedeventList, eventList);
//    }
//
//    @Test
//    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, ()
//                -> eventList.asUnmodifiableObservableList().remove(0));
//    }
//
}