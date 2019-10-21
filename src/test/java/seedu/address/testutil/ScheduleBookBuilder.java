package seedu.address.testutil;

import seedu.address.model.DataBook;
import seedu.address.model.schedule.Schedule;

/**
 * A utility class to help with building {@code Schedule} {@code DataBook}.
 * Example usage: <br>
 *     {@code DataBook<Schedule> sb = new ScheduleBookBuilder().withSchedule(...).build();}
 */
public class ScheduleBookBuilder {

    private DataBook<Schedule> scheduleBook;

    public ScheduleBookBuilder() {
        scheduleBook = new DataBook<>();
    }

    public ScheduleBookBuilder(DataBook<Schedule> scheduleBook) {
        this.scheduleBook = scheduleBook;
    }

    /**
     * Adds a new {@code Schedule} to the {@code DataBook} that we are building.
     */
    public ScheduleBookBuilder withSchedule(Schedule schedule) {
        scheduleBook.add(schedule);
        return this;
    }

    public DataBook<Schedule> build() {
        return scheduleBook;
    }
}
