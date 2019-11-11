package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.testutil.personutil.PersonBuilder;
import seedu.address.testutil.scheduleutil.TypicalEvents;
import seedu.address.testutil.scheduleutil.TypicalSchedule;

class ScheduleTest {

    private Person alice;
    private Person benson;

    @BeforeEach
    void init() {
        alice = new PersonBuilder(ALICE).build();
        benson = new PersonBuilder(BENSON).build();
    }

    @Test
    void addEvent() throws EventClashException {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(alice.getPersonId());
        schedule.addEvent(TypicalEvents.generateTypicalEvent1());

        assertTrue(schedule.getEvents().get(0).equals(TypicalEvents.generateTypicalEvent1()));
        assertFalse(schedule.getEvents().get(0).equals(TypicalEvents.generateTypicalEvent2()));
    }

    @Test
    void addEvent_clash() {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(alice.getPersonId());

        assertDoesNotThrow(() ->
                schedule.addEvent(TypicalEvents.generateTypicalEvent1()));

        assertThrows(EventClashException.class, () ->
                schedule.addEvent(TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void addEvent_eventExists() {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(alice.getPersonId());

        Event event1 = TypicalEvents.generateEmptyEvent();
        event1.addTimeslot(TIME_SLOT1);

        Event event2 = TypicalEvents.generateEmptyEvent();
        event1.addTimeslot(TIME_SLOT2);

        assertDoesNotThrow(() -> schedule.addEvent(event1));
        assertDoesNotThrow(() -> schedule.addEvent(event2));

    }

    @Test
    void deleteEvent_success() {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(alice.getPersonId());

        assertDoesNotThrow(() ->
                schedule.addEvent(TypicalEvents.generateTypicalEvent1()));

        assertDoesNotThrow(() ->
                schedule.deleteEvent(EVENT_NAME1));
    }

    @Test
    void getEvents() throws EventClashException {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(alice.getPersonId());
        schedule.addEvent(TypicalEvents.generateTypicalEvent2());

        assertTrue(schedule.getEvents().get(0).equals(TypicalEvents.generateTypicalEvent2()));
        assertFalse(schedule.getEvents().get(0).equals(TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void getPersonId() {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(alice.getPersonId());
        assertTrue(alice.getPersonId().equals(schedule.getPersonId()));
        assertFalse(benson.getPersonId().equals(schedule.getPersonId()));
    }

    @Test
    void toStringTest() throws EventClashException {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(alice.getPersonId());
        assertEquals(schedule.toString(), schedule.toString());

        schedule.addEvent(TypicalEvents.generateTypicalEvent1());
        assertEquals(schedule.toString(), schedule.toString());
    }
}
