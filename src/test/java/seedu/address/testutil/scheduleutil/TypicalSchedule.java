package seedu.address.testutil.scheduleutil;

import seedu.address.model.person.PersonId;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.model.person.exceptions.EventClashException;
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
    public static Schedule generateTypicalSchedule(PersonId personId) throws EventClashException {
        Schedule schedule = new Schedule(personId);
        try {
            schedule.addEvent(TypicalEvents.generateTypicalEvent1());
            schedule.addEvent(TypicalEvents.generateTypicalEvent2());
        } catch (DuplicateEventException e) {
            e.printStackTrace();
        }
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
