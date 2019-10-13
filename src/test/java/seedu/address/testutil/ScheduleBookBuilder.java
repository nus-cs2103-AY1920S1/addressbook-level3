package seedu.address.testutil;

import seedu.address.model.ScheduleBook;
import seedu.address.model.schedule.Schedule;

/**
 * A utility class to help with building ScheduleBook objects.
 * Example usage: <br>
 *     {@code ScheduleBook sb = new ScheduleBookBuilder().withSchedule(...).build();}
 */
public class ScheduleBookBuilder {

    private ScheduleBook scheduleBook;

    public ScheduleBookBuilder() {
        scheduleBook = new ScheduleBook();
    }

    public ScheduleBookBuilder(ScheduleBook scheduleBook) {
        this.scheduleBook = scheduleBook;
    }

    /**
     * Adds a new {@code Schedule} to the {@code ScheduleBook} that we are building.
     */
    public ScheduleBookBuilder withSchedule(Schedule schedule) {
        scheduleBook.addSchedule(schedule);
        return this;
    }

    public ScheduleBook build() {
        return scheduleBook;
    }
}
