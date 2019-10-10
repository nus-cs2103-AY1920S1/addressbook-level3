package seedu.address.model.person;

import java.util.NavigableSet;
import java.util.TreeSet;

import seedu.address.model.Duration;
import seedu.address.model.person.exceptions.SchedulingException;


/**
 * Manages the availability of the owner.
 */
public class Schedule {
    public static final String MESSAGE_SCHEDULE_CONFLICT = "The duration conflicts with the existing schedule.";
    public static final String MESSAGE_OUTSIDE_WORKING_HOURS = "The person does not work during the specified time.";
    public static final String MESSAGE_EMPTY_SCHEDULE = "No task assigned";

    private static final String START_WORK_TIME = "0900";
    private static final String END_WORK_TIME = "1800";

    private NavigableSet<Duration> schedule;
    private Duration workingHours;


    /**
     * Constructs a schedule within the working hours.
     */
    public Schedule() {
        this.schedule = new TreeSet<>();
        this.workingHours = Duration.parse(START_WORK_TIME, END_WORK_TIME);
    }

    /**
     * Blocks off the owner's schedule with the given duration.
     *
     * @param duration incoming task
     * @throws SchedulingException when the duration is outside working hours, or conflicts with the existing schedule
     */
    public void add(Duration duration) throws SchedulingException {
        if (!isInWorkingHours(duration)) {
            throw new SchedulingException(MESSAGE_OUTSIDE_WORKING_HOURS);
        }

        if (!isAvailable(duration)) {
            throw new SchedulingException(MESSAGE_SCHEDULE_CONFLICT);
        }

        if (!schedule.add(duration)) {
            throw new SchedulingException("An unknown error has occurred.");
        }
    }

    /**
     * Removes the scheduled event.
     *
     * @param duration an existing event in the owner's schedule
     * @return true when the event exists in the schedule, and removed successfully
     */
    public boolean remove(Duration duration) {
        return schedule.remove(duration);
    }


    private boolean isInWorkingHours(Duration duration) {
        return (duration.getEnd().compareTo(duration.getStart()) > 0)
                && (duration.getStart().compareTo(workingHours.getStart()) >= 0)
                && (duration.getEnd().compareTo(workingHours.getEnd()) <= 0);
    }


    /**
     * Checks whether the incoming duration clashes with the existing schedule.
     *
     * @param duration task duration
     * @return true if the duration clashes
     */
    private boolean isAvailable(Duration duration) {
        Duration previous = schedule.floor(duration);
        Duration next = schedule.ceiling(duration);

        if ((previous != null) && (duration.overlaps(previous))) {
            return false;
        }

        if ((next != null) && (duration.overlaps(next))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return this.schedule.stream()
                .map(Duration::to24HrString)
                .reduce((str1, str2) -> str1 + ", " + str2)
                .orElse(MESSAGE_EMPTY_SCHEDULE);
    }
}


