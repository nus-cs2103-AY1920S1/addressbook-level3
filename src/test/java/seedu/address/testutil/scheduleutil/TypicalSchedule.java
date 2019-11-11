package seedu.address.testutil.scheduleutil;

import seedu.address.model.person.PersonId;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.model.util.SampleEvents;

/**
 * Typical Schedule of a Person.
 */
public class TypicalSchedule {

    /**
     * Generates a typical Schedule of a person with PersonId.
     */
    public static Schedule generateTypicalSchedule(PersonId personId) throws EventClashException {
        Schedule schedule = new Schedule(personId);
        schedule.addEvent(TypicalEvents.generateTypicalEvent1());
        schedule.addEvent(TypicalEvents.generateTypicalEvent2());

        return schedule;
    }

    /**
     * Generates an empty Schedule of a person with PersonId.
     */
    public static Schedule generateEmptySchedule(PersonId personId) {
        Schedule schedule = new Schedule(personId);
        return schedule;
    }

    /**
     * Generates a typical sample schedule.
     */
    public static Schedule generateTypicalSampleSchedule1(PersonId personId) {
        Schedule schedule = new Schedule(personId);
        try {
            schedule.addEvent(SampleEvents.EVENT1_1);
            schedule.addEvent(SampleEvents.EVENT1_2);
        } catch (EventClashException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    /**
     * Generates a typical sample schedule.
     */
    public static Schedule generateTypicalSampleSchedule2(PersonId personId) {
        Schedule schedule = new Schedule(personId);
        try {
            schedule.addEvent(SampleEvents.EVENT2_1);
            schedule.addEvent(SampleEvents.EVENT2_2);
        } catch (EventClashException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    /**
     * Generates a typical sample schedule.
     */
    public static Schedule generateTypicalSampleSchedule3(PersonId personId) {
        Schedule schedule = new Schedule(personId);
        try {
            schedule.addEvent(SampleEvents.EVENT3_1);
            schedule.addEvent(SampleEvents.EVENT3_2);
        } catch (EventClashException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    /**
     * Generates a typical sample schedule.
     */
    public static Schedule generateTypicalSampleSchedule4(PersonId personId) {
        Schedule schedule = new Schedule(personId);
        try {
            schedule.addEvent(SampleEvents.EVENT4_1);
            schedule.addEvent(SampleEvents.EVENT4_2);
        } catch (EventClashException e) {
            e.printStackTrace();
        }
        return schedule;
    }


}
