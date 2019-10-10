package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.Duration;
import seedu.address.model.person.exceptions.SchedulingException;

class ScheduleTest {
    @Test
    void add_outsideWorkingHours_throwsSchedulingException() {
        Schedule schedule = new Schedule();
        Duration outsideWorkingHours = Duration.parse("0200", "0400");

        Exception thrown = assertThrows(SchedulingException.class, () -> schedule.add(outsideWorkingHours));
        assertEquals(Schedule.MESSAGE_OUTSIDE_WORKING_HOURS, thrown.getMessage());
    }

    @Test
    void add_conflictingTask_throwsSchedulingException() {
        Schedule schedule = new Schedule();
        Duration conflict = Duration.parse("0930", "1130");

        schedule.add(Duration.parse("0900", "1000"));
        schedule.add(Duration.parse("1100", "1400"));

        Exception thrown = assertThrows(SchedulingException.class, () -> schedule.add(conflict));
        assertEquals(Schedule.MESSAGE_SCHEDULE_CONFLICT, thrown.getMessage());
    }

    @Test
    void remove_isSuccessful() {
        Schedule schedule = new Schedule();
        Duration slot = Duration.parse("0900", "1000");

        schedule.add(slot);
        schedule.remove(slot);

        assertEquals(Schedule.MESSAGE_EMPTY_SCHEDULE, schedule.toString());

    }

}
