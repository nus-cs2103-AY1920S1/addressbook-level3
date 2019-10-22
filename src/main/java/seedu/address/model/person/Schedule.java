package seedu.address.model.person;

import java.time.Duration;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.TreeSet;

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


    public String getSchedulingSuggestion(EventTime eventTime) {
        String suggested = findFirstAvailableSlot(eventTime)
                .map(x -> String.format(MESSAGE_SUGGEST_TIME_FORMAT, x.toString()))
                .orElse("");

        String returnSuggestion = suggested.isEmpty() ? "" : "\n" + suggested;
        if (isOutsideWorkingHours(eventTime)) {
            return MESSAGE_OUTSIDE_WORKING_HOURS + returnSuggestion;
        }

        if (!isAvailable(eventTime)) {
            return MESSAGE_SCHEDULE_CONFLICT + returnSuggestion;
        }

        return suggested;
    }

    /**
     * Blocks off the owner's schedule with the given duration.
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
     *
     * @param proposed a proposed time slot
     * @return Optional of the earliest EventTime that can fit in the schedule; if the proposed time is already the
     * earliest, return an Optional of the proposed time; if no slot available, return an empty Optional.
     */
    public Optional<EventTime> findFirstAvailableSlot(EventTime proposed) {
        Duration length = proposed.getDuration();

        EventTime lastCandidate = schedule.ceiling(proposed);
        NavigableSet<EventTime> candidates = schedule.headSet(lastCandidate, true);
        Iterator<EventTime> iter = candidates.iterator();

        EventTime prev = null;
        if (iter.hasNext()) {
            prev = iter.next();
        }

        while (iter.hasNext()) {
            EventTime head = iter.next();
            boolean canFit = Duration.between(prev.getEnd(), head.getStart()).compareTo(length) >= 0;

            if (canFit) {
                return Optional.of(new EventTime(prev.getEnd(), length));
            }

            prev = head;
        }

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
}


