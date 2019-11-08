package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.EventTime;
import seedu.address.model.person.exceptions.SchedulingException;

class ScheduleTest {
    public static final LocalTime TEN_AM = LocalTime.of(10, 0);


    /**
     * Gets a sample schedule with two events.
     *
     * @return sample schedule
     */
    Schedule sampleSchedule() {
        Schedule schedule = new Schedule();
        schedule.add(EventTime.parse("900", "1000"));
        schedule.add(EventTime.parse("1200", "1500"));
        return schedule;
    }

    @Test
    void add_outsideWorkingHours_throwsSchedulingException() {
        Schedule schedule = new Schedule();
        EventTime outsideWorkingHours = EventTime.parse("0200", "0400");

        Exception thrown = assertThrows(SchedulingException.class, () -> schedule.add(outsideWorkingHours));
        assertEquals(Schedule.MESSAGE_OUTSIDE_WORKING_HOURS, thrown.getMessage());
    }

    @Test
    void add_conflictingTask_throwsSchedulingException() {
        Schedule schedule = new Schedule();
        EventTime conflict = EventTime.parse("0930", "1130");

        schedule.add(EventTime.parse("0900", "1000"));
        schedule.add(EventTime.parse("1100", "1400"));

        Exception thrown = assertThrows(SchedulingException.class, () -> schedule.add(conflict));
        assertEquals(Schedule.MESSAGE_SCHEDULE_CONFLICT, thrown.getMessage());
    }

    @Test
    void remove_isSuccessful() {
        Schedule schedule = new Schedule();
        EventTime slot = EventTime.parse("0900", "1000");

        schedule.add(slot);
        schedule.remove(slot);

        assertEquals(Schedule.MESSAGE_EMPTY_SCHEDULE, schedule.toString());

    }

    @Test
    void findFirstAvailableSlot_lateButAvail_returnsEarlySlot() {
        Schedule sample = sampleSchedule();
        EventTime expected = EventTime.parse("1000", "1100");
        EventTime oneHourTask = EventTime.parse("1500", "1600");
        assertEquals(expected, sample.findFirstAvailableSlot(expected.getStart(), oneHourTask.getDuration()).get());
    }

    @Test
    void findFirstAvailableSlot_schedulingConflict_returnsAvailableSlot() {
        Schedule sample = sampleSchedule();
        EventTime oneHourTask = EventTime.parse("1400", "1500");
        EventTime expected = EventTime.parse("1000", "1100");
        assertEquals(expected, sample.findFirstAvailableSlot(expected.getStart(), oneHourTask.getDuration()).get());
    }

    @Test
    void findFirstAvailableSlot_alreadyEarliest_returnsItself() {
        Schedule sample = sampleSchedule();
        EventTime threeHourTask = EventTime.parse("1500", "1800");
        assertEquals(threeHourTask, sample.findFirstAvailableSlot(TEN_AM, threeHourTask.getDuration()).get());
    }

    @Test
    void findFirstAvailableSlot_notAvailable_returnsEmpty() {
        Schedule sample = sampleSchedule();
        EventTime fourHourTask = EventTime.parse("1400", "1800");
        assertTrue(sample.findFirstAvailableSlot(TEN_AM, fourHourTask.getDuration()).isEmpty());
    }


}
