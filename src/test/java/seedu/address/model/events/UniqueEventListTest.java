//@@author SakuraBlossom
package seedu.address.model.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.TypicalEvents.EVENT_ALICE;
import static seedu.address.testutil.TypicalEvents.EVENT_BENSON;
import static seedu.address.testutil.TypicalEvents.EVENT_CARL;
import static seedu.address.testutil.TypicalEvents.EVENT_DANIEL;
import static seedu.address.testutil.TypicalEvents.EVENT_ELLE;
import static seedu.address.testutil.TypicalEvents.EVENT_FIONA;
import static seedu.address.testutil.TypicalEvents.EVENT_GEORGE;
import static seedu.address.testutil.TypicalEvents.getTypicalAppointment;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

class UniqueEventListTest {

    private UniqueEventList eventList = new UniqueEventList();

    @BeforeEach
    void setup() {
        for (Event event : getTypicalAppointment()) {
            eventList.add(event);
        }
    }

    @Test
    void getEventsInOrder() {
        assertEquals(getTypicalAppointment().toString(),
                Arrays.asList(eventList.asUnmodifiableObservableList().toArray()).toString());

        List<Event> typicalAppointments = getTypicalAppointment();
        UniqueEventList newEventList = new UniqueEventList();
        for (int idx = typicalAppointments.size() - 1; idx >= 0; idx--) {
            newEventList.add(typicalAppointments.get(idx));
        }

        assertEquals(getTypicalAppointment().toString(),
                Arrays.asList(newEventList.asUnmodifiableObservableList().toArray()).toString());
    }

    @Test
    void getEventsInConflict() {
        assertEquals(Arrays.asList(EVENT_ALICE), eventList.getListOfEventsInConflict(EVENT_ALICE));
        assertEquals(Arrays.asList(EVENT_BENSON, EVENT_CARL, EVENT_DANIEL),
                eventList.getListOfEventsInConflict(EVENT_BENSON));
        assertEquals(Arrays.asList(EVENT_BENSON, EVENT_CARL, EVENT_DANIEL),
                eventList.getListOfEventsInConflict(EVENT_CARL));

        assertEquals(Arrays.asList(EVENT_BENSON, EVENT_CARL, EVENT_DANIEL),
                eventList.getListOfEventsInConflict(EVENT_DANIEL));
        assertEquals(Arrays.asList(EVENT_ELLE),
                eventList.getListOfEventsInConflict(EVENT_ELLE));
        assertEquals(Arrays.asList(EVENT_FIONA),
                eventList.getListOfEventsInConflict(EVENT_FIONA));
        assertEquals(Arrays.asList(EVENT_GEORGE),
                eventList.getListOfEventsInConflict(EVENT_GEORGE));

        assertEquals(Arrays.asList(),
                eventList.getListOfEventsInConflict(
                new EventBuilder(EVENT_DANIEL)
                        .withStartTime(1, 0, 0, 0, 0, 30)
                        .build()));
    }

    @Test
    void countNumberOfEventsInConflict() {
        assertEquals(1, eventList.countNumberOfEventsInConflict(EVENT_ALICE));
        assertEquals(3, eventList.countNumberOfEventsInConflict(EVENT_BENSON));
        assertEquals(3, eventList.countNumberOfEventsInConflict(EVENT_CARL));
        assertEquals(3, eventList.countNumberOfEventsInConflict(EVENT_DANIEL));
        assertEquals(1, eventList.countNumberOfEventsInConflict(EVENT_ELLE));
        assertEquals(1, eventList.countNumberOfEventsInConflict(EVENT_FIONA));
        assertEquals(1, eventList.countNumberOfEventsInConflict(EVENT_GEORGE));

        assertEquals(0, eventList.countNumberOfEventsInConflict(
                new EventBuilder(EVENT_DANIEL)
                        .withStartTime(1, 0, 0, 0, 0, 30)
                        .build()));
    }

    @Test
    void allowedToSchedule_noLimitConcurrentEvents() {
        assertFalse(eventList.allowedToSchedule(EVENT_ALICE, Integer.MAX_VALUE));
        assertFalse(eventList.allowedToSchedule(EVENT_BENSON, Integer.MAX_VALUE));
        assertFalse(eventList.allowedToSchedule(EVENT_CARL, Integer.MAX_VALUE));
        assertFalse(eventList.allowedToSchedule(EVENT_DANIEL, Integer.MAX_VALUE));
        assertFalse(eventList.allowedToSchedule(EVENT_ELLE, Integer.MAX_VALUE));
        assertFalse(eventList.allowedToSchedule(EVENT_FIONA, Integer.MAX_VALUE));
        assertFalse(eventList.allowedToSchedule(EVENT_GEORGE, Integer.MAX_VALUE));

        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_BENSON)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), Integer.MAX_VALUE));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ALICE)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), Integer.MAX_VALUE));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withStartTime(1, 0, 0, 0, 0, 30)
                        .build(), Integer.MAX_VALUE));

        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), Integer.MAX_VALUE));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), Integer.MAX_VALUE));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_CARL.getPersonId())
                        .build(), Integer.MAX_VALUE));

        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), Integer.MAX_VALUE));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), Integer.MAX_VALUE));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_CARL.getPersonId())
                        .build(), Integer.MAX_VALUE));
    }

    @Test
    void allowedToSchedule_limitToTwoConcurrentEvents() {
        assertFalse(eventList.allowedToSchedule(EVENT_ALICE, 2));
        assertFalse(eventList.allowedToSchedule(EVENT_BENSON, 2));
        assertFalse(eventList.allowedToSchedule(EVENT_CARL, 2));
        assertFalse(eventList.allowedToSchedule(EVENT_DANIEL, 2));
        assertFalse(eventList.allowedToSchedule(EVENT_ELLE, 2));
        assertFalse(eventList.allowedToSchedule(EVENT_FIONA, 2));
        assertFalse(eventList.allowedToSchedule(EVENT_GEORGE, 2));

        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_BENSON)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), 2));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ALICE)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), 2));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withStartTime(1, 0, 0, 0, 0, 30)
                        .build(), 2));

        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), 2));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), 2));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_CARL.getPersonId())
                        .build(), 2));

        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), 2));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), 2));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_CARL.getPersonId())
                        .build(), 2));
    }

    @Test
    void allowedToSchedule_limitToOneConcurrentEvents() {
        assertFalse(eventList.allowedToSchedule(EVENT_ALICE, 1));
        assertFalse(eventList.allowedToSchedule(EVENT_BENSON, 1));
        assertFalse(eventList.allowedToSchedule(EVENT_CARL, 1));
        assertFalse(eventList.allowedToSchedule(EVENT_DANIEL, 1));
        assertFalse(eventList.allowedToSchedule(EVENT_ELLE, 1));
        assertFalse(eventList.allowedToSchedule(EVENT_FIONA, 1));
        assertFalse(eventList.allowedToSchedule(EVENT_GEORGE, 1));

        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_BENSON)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), 1));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ALICE)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), 1));
        assertTrue(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withStartTime(1, 0, 0, 0, 0, 30)
                        .build(), 1));

        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), 1));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), 1));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_DANIEL)
                        .withId(EVENT_CARL.getPersonId())
                        .build(), 1));

        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_ALICE.getPersonId())
                        .build(), 1));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_BENSON.getPersonId())
                        .build(), 1));
        assertFalse(eventList.allowedToSchedule(
                new EventBuilder(EVENT_ELLE)
                        .withId(EVENT_CARL.getPersonId())
                        .build(), 1));
    }
}
