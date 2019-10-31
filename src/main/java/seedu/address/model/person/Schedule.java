package seedu.address.model.person;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.model.EventTime;
import seedu.address.model.person.exceptions.SchedulingException;


/**
 * Manages the availability of the owner.
 */
public class Schedule {
    public static final String MESSAGE_EMPTY_SCHEDULE = "No task assigned";
    public static final String MESSAGE_SUGGEST_TIME_FORMAT = "Suggested Time: %s";
    public static final String MESSAGE_SCHEDULE_CONFLICT = "The duration conflicts with the existing schedule.";
    public static final String MESSAGE_OUTSIDE_WORKING_HOURS = "The person does not work during the specified time.";
    public static final String MESSAGE_EVENT_START_BEFORE_NOW = "The proposed time is in the past.";

    private static final String START_WORK_TIME = "0900";
    private static final String END_WORK_TIME = "1800";

    private NavigableSet<EventTime> schedule;
    private EventTime workingHours;


    /**
     * Constructs a schedule within the working hours.
     */
    public Schedule() {
        this.schedule = new TreeSet<>();
        this.workingHours = EventTime.parse(START_WORK_TIME, END_WORK_TIME);

        EventTime beforeWorkingHours = EventTime.parse("0000", START_WORK_TIME);
        EventTime afterWorkingHours = EventTime.parse(END_WORK_TIME, "2359");
        schedule.add(beforeWorkingHours);
        schedule.add(afterWorkingHours);
    }


    public String getSchedulingSuggestion(EventTime eventTime, LocalTime timeNow) {
        String suggested = findFirstAvailableSlot(eventTime, timeNow)
                .filter(x -> !x.equals(eventTime)) // check if the suggested time is different from proposed
                .map(x -> String.format(MESSAGE_SUGGEST_TIME_FORMAT, x.toString()))
                .orElse("");

        String returnSuggestion = suggested.isEmpty() ? "" : "\n" + suggested;


        if (isOutsideWorkingHours(eventTime)) {
            return MESSAGE_OUTSIDE_WORKING_HOURS + returnSuggestion;
        }

        if (!isAvailable(eventTime)) {
            return MESSAGE_SCHEDULE_CONFLICT + returnSuggestion;
        }

        if (suggested.isEmpty()) {
            // no suggestion, the command is good
            return suggested;
        }

        // has suggestion but dismissible
        return suggested + "\n" + AssignCommand.MESSAGE_PROMPT_FORCE;
    }

    /**
     * Blocks off the owner's schedule with the given duration. This action doesn't check the current time.
     *
     * @param eventTime incoming task
     */
    public void add(EventTime eventTime) throws SchedulingException {
        if (isOutsideWorkingHours(eventTime)) {
            throw new SchedulingException(MESSAGE_OUTSIDE_WORKING_HOURS);
        }

        if (!isAvailable(eventTime)) {
            throw new SchedulingException(MESSAGE_SCHEDULE_CONFLICT);
        }

        if (!schedule.add(eventTime)) {
            throw new SchedulingException("An unknown error has occurred.");
        }
    }


    /**
     * Finds the earliest available EventTime has the same length of proposed, and fits in the schedule.
     * This method will check against the current time.
     *
     * @param proposed a proposed time slot
     * @param timeNow  time now
     * @return Optional of the earliest EventTime that can fit in the schedule; if the proposed time is already the
     * earliest, return an Optional of the proposed time; if no slot available, return an empty Optional.
     */
    public Optional<EventTime> findFirstAvailableSlot(EventTime proposed, LocalTime timeNow) {
        Duration length = proposed.getDuration();

        // get a view of the schedule, from system time to the last EventTime in the schedule
        EventTime lastCandidate = schedule.last();

        // HACK: using a zero minute event time to get the tailset
        EventTime now = new EventTime(timeNow, timeNow);
        schedule.add(now);

        NavigableSet<EventTime> candidates = schedule.subSet(now, true, lastCandidate, true);
        Iterator<EventTime> iter = candidates.iterator();

        EventTime prev = null;
        if (iter.hasNext()) {
            prev = iter.next();
        }

        while (iter.hasNext()) {
            EventTime head = iter.next();
            boolean canFit = Duration.between(prev.getEnd(), head.getStart()).compareTo(length) >= 0;

            if (canFit) {
                schedule.remove(now);
                return Optional.of(new EventTime(prev.getEnd(), length));
            }

            prev = head;
        }

        schedule.remove(now);
        return Optional.empty();
    }

    /**
     * Removes the scheduled event.
     *
     * @param eventTime an existing event in the owner's schedule
     * @return true when the event exists in the schedule, and removed successfully
     */
    public boolean remove(EventTime eventTime) {
        return schedule.remove(eventTime);
    }


    private boolean isOutsideWorkingHours(EventTime eventTime) {
        return (eventTime.getEnd().compareTo(eventTime.getStart()) <= 0)
                || (eventTime.getStart().compareTo(workingHours.getStart()) < 0)
                || (eventTime.getEnd().compareTo(workingHours.getEnd()) > 0);
    }


    /**
     * Checks whether the incoming duration clashes with the existing schedule.
     *
     * @param eventTime task duration
     * @return true if the duration clashes
     */
    public boolean isAvailable(EventTime eventTime) {
        EventTime previous = schedule.floor(eventTime);
        EventTime next = schedule.ceiling(eventTime);

        if ((previous != null) && (eventTime.overlaps(previous))) {
            return false;
        }
        if ((next != null) && (eventTime.overlaps(next))) {
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        return this.schedule
                .subSet(EventTime.parse("0000", START_WORK_TIME), false,
                        EventTime.parse(END_WORK_TIME, "2359"), false).stream()
                .map(EventTime::to24HrString)
                .reduce((str1, str2) -> str1 + ", " + str2)
                .orElse(MESSAGE_EMPTY_SCHEDULE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule1 = (Schedule) o;
        return this.schedule.equals(schedule1.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schedule);
    }
}


