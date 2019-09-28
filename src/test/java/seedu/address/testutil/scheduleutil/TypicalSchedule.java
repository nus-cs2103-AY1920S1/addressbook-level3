package seedu.address.testutil.scheduleutil;

import seedu.address.model.person.PersonId;
import seedu.address.model.person.schedule.Schedule;

/**
 * Typical Schedule of a Person.
 */
public class TypicalSchedule {

    /**
     * Generates a typical Schedule of a person with PersonId.
     *
     * @param personId of the Person
     * @return Schedule
     */
    public static Schedule generateTypicalSchedule(PersonId personId) {
        Schedule schedule = new Schedule(personId);
        schedule.addEvent(TypicalEvents.generateTypicalEvent1());
        schedule.addEvent(TypicalEvents.generateTypicalEvent2());
        return schedule;
    }

    /**
     * Generates an empty Schedule of a person with PersonId.
     *
     * @param personId of the Person
     * @return Schedule
     */
    public static Schedule generateEmptySchedule(PersonId personId) {
        Schedule schedule = new Schedule(personId);
        return schedule;
    }

}
