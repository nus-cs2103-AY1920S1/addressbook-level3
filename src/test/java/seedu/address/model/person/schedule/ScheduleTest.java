package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
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
    void addEvent() {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(alice.getPersonId());
        schedule.addEvent(TypicalEvents.generateTypicalEvent1());

        assertTrue(schedule.getEvents().get(0).equals(TypicalEvents.generateTypicalEvent1()));
        assertFalse(schedule.getEvents().get(0).equals(TypicalEvents.generateTypicalEvent2()));
    }

    @Test
    void getEvents() {
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
}
