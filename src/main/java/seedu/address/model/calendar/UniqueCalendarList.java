package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.calendar.exceptions.CalendarNotFoundException;
import seedu.address.model.calendar.exceptions.DuplicateCalendarException;
import seedu.address.model.member.MemberName;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;



/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Task#isSameTask(Task)}. As such, adding and updating of
 * persons uses Task#isSameTask(Task) for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a task uses Task#equals(Object) so
 * as to ensure that the task with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#isSameTask(Task)
 */
public class UniqueCalendarList implements Iterable<CalendarWrapper> {

    private final ObservableList<CalendarWrapper> internalList = FXCollections.observableArrayList();
    private final ObservableList<CalendarWrapper> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private static final int END_HOUR = 22;
    private static final int END_MINUTE = 00;
//    private static final TimeZone DEFAULT_TIMEZONE = TimeZoneRegistryFactory
//            .getInstance()
//            .createRegistry()
//            .getTimeZone("Asia/Hong_Kong");

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(CalendarWrapper toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCalendar);
    }

    public boolean containsMemberName(MemberName toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    public void add(CalendarWrapper toAdd) {
        requireNonNull(toAdd);
        if (containsMemberName(toAdd.getMemberName())) {
            throw new DuplicateCalendarException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setCalendar(CalendarWrapper target, CalendarWrapper editedCalendar) {
        requireAllNonNull(target, editedCalendar);

        int index = getIndexOf(target);
        if (!target.isSameCalendar(editedCalendar) && contains(editedCalendar)) {
            throw new DuplicateCalendarException();
        }

        internalList.set(index, editedCalendar);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(CalendarWrapper toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CalendarNotFoundException();
        }
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(MemberName toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CalendarNotFoundException();
        }
    }

    /**
     * Clears the task list of all tasks.
     */
    public void clearAll() {
        internalList.clear();
    }

    public void setCalendars(UniqueCalendarList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setCalendars(List<CalendarWrapper> calendars) {
        requireAllNonNull(calendars);
        if (!calendarsAreUnique(calendars)) {
            throw new DuplicateCalendarException();
        }

        internalList.setAll(calendars);
    }

    public int getIndexOf(CalendarWrapper target) {
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CalendarNotFoundException();
        }
        return index;
    }

    public int getIndexOf(MemberName target) {
        int count = 0;
        boolean found = false;
        for (CalendarWrapper calendar : internalList) {
            if (calendar.hasMemberName(target)) {
                found = true;
            }
            count ++;
        }
        if (!found) {
            throw new CalendarNotFoundException();
        }
        return count;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CalendarWrapper> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CalendarWrapper> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCalendarList // instanceof handles nulls
                && internalList.equals(((UniqueCalendarList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tasks} contains only unique tasks.
     */
    private boolean calendarsAreUnique(List<CalendarWrapper> calendars) {
        for (int i = 0; i < calendars.size() - 1; i++) {
            for (int j = i + 1; j < calendars.size(); j++) {
                if (calendars.get(i).isSameCalendar(calendars.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }


    // ========= Funtional Commands ===========================================================================

    public static LocalDateTime roundUpToNearestHalfHour(LocalDateTime localDateTime) {
        int minutes = localDateTime.getMinute();
        int seconds = localDateTime.getSecond();
        if (minutes % 30 == 0 && seconds == 0) {
            return localDateTime;
        } else if (minutes > 30 || (minutes == 30 && seconds > 0)) {
            return localDateTime
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0)
                    .plusHours(1);
        } else {
            return localDateTime
                    .withMinute(30)
                    .withSecond(0)
                    .withNano(0);
        }
    }

    public static LocalDateTime roundDownToNearestHalfHour(LocalDateTime localDateTime) {
        int minutes = localDateTime.getMinute();
        int seconds = localDateTime.getSecond();
        if (minutes % 30 == 0 && seconds == 0) {
            return localDateTime;
        } else if (minutes > 30 || (minutes == 30 && seconds > 0)) {
            return localDateTime
                    .withMinute(30)
                    .withSecond(0)
                    .withNano(0);
        } else {
            return localDateTime
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
        }
    }

    private static final LocalTime DEFAULT_START_TIME = LocalTime.of(8, 0, 0, 0);
    private static final LocalTime DEFAULT_END_TIME = LocalTime.of(22, 0, 0, 0);

    public static LocalDateTime adjustStartDate(LocalDateTime dateTime) {
        LocalDateTime roundedDateTime = roundUpToNearestHalfHour(dateTime);
        LocalTime roundedTime = roundedDateTime.toLocalTime();
        if (roundedTime.isBefore(DEFAULT_START_TIME)) {
            return LocalDateTime.of(roundedDateTime.toLocalDate(), DEFAULT_START_TIME);
        } else if (roundedTime.isAfter(DEFAULT_END_TIME)) {
            return LocalDateTime.of(roundedDateTime.toLocalDate().plusDays(1), DEFAULT_START_TIME);
        } else {
            return roundedDateTime;
        }
    }

    public static LocalDateTime adjustEndDate(LocalDateTime dateTime) {
        LocalDateTime roundedDateTime = roundDownToNearestHalfHour(dateTime);
        LocalTime roundedTime = roundedDateTime.toLocalTime();
        if (roundedTime.isAfter(DEFAULT_END_TIME)) {
            return LocalDateTime.of(roundedDateTime.toLocalDate(), DEFAULT_END_TIME);
        } else if (roundedTime.isBefore(DEFAULT_START_TIME)) {
            return LocalDateTime.of(roundedDateTime.toLocalDate().minusDays(1), DEFAULT_END_TIME);
        } else {
            return roundedDateTime;
        }
    }

    public static List<LocalDateTime> generateTimeslots(LocalDateTime lcdStartDate, LocalDateTime lcdEndDate,
                                                        Duration meetingDuration, LocalTime lastDailyMeetingTime) {
        LocalDateTime adjustedStartDate = adjustStartDate(lcdStartDate);
        LocalDateTime adjustedEndDate = adjustEndDate(lcdEndDate);

        List<LocalDateTime> timeslots = new ArrayList<>();

        LocalDateTime lastPossibleMeetingDateTime = adjustedEndDate.minus(meetingDuration);
        LocalDate lastPossibleMeetingDate = lastPossibleMeetingDateTime.toLocalDate();
        LocalTime lastPossibleMeetingTime = lastPossibleMeetingDateTime.toLocalTime();

        LocalDateTime currentMeetingDateTime = adjustedStartDate;
        LocalDate currentMeetingDate = currentMeetingDateTime.toLocalDate();
        LocalTime currentMeetingTime = currentMeetingDateTime.toLocalTime();


        while (currentMeetingDate.isBefore(lastPossibleMeetingDate)) {
            while (currentMeetingTime.isBefore(lastDailyMeetingTime) ||
                    currentMeetingTime.equals(lastDailyMeetingTime)) {
                LocalDateTime newMeetingDateTime = LocalDateTime.of(currentMeetingDate, currentMeetingTime);
                currentMeetingTime = currentMeetingTime.plusMinutes(30);
                timeslots.add(newMeetingDateTime);
            }
            currentMeetingDate = currentMeetingDate.plusDays(1);
            currentMeetingTime = DEFAULT_START_TIME;
        }

        while (currentMeetingTime.isBefore(lastPossibleMeetingTime) ||
                currentMeetingDate.equals(lastPossibleMeetingTime)) {
            LocalDateTime newMeetingDateTime = LocalDateTime.of(currentMeetingDate, currentMeetingTime);
            currentMeetingTime = currentMeetingTime.plusMinutes(30);
            timeslots.add(newMeetingDateTime);
        }
        return timeslots;
    }

    public static List<LocalDateTime> generateMemberAvailibility(PeriodList availibilityPeriods,
                                                                 Duration meetingDuration,
                                                                 LocalTime lastDailyMeetingTime) {
        availibilityPeriods = filterPeriodsShorterThan(availibilityPeriods, meetingDuration);
        Iterator<Period> availibityPeriodIter = availibilityPeriods.iterator();
        List<LocalDateTime> completeList = new ArrayList<LocalDateTime>();
        while (availibityPeriodIter.hasNext()) {
            Period availibityPeriod = availibityPeriodIter.next();
            LocalDateTime lcdStartDate = LocalDateTime.ofInstant(
                    availibityPeriod.getStart().toInstant(),
                    ZoneId.systemDefault());
            LocalDateTime lcdEndDate = LocalDateTime.ofInstant(
                    availibityPeriod.getEnd().toInstant(),
                    ZoneId.systemDefault());
            completeList.addAll(
                    generateTimeslots(lcdStartDate, lcdEndDate, meetingDuration, lastDailyMeetingTime));
        }
        return completeList;
    }

    public LinkedHashMap<LocalDateTime, Integer> generateAttendance(LocalDateTime lcdStartDate,
                                                                    LocalDateTime lcdEndDate,
                                                                    Duration meetingDuration,
                                                                    net.fortuna.ical4j.model.Period searchPeriod) {
        LocalTime lastDailyMeetingTime = DEFAULT_END_TIME.minus(meetingDuration);
        List<LocalDateTime> meetingTimeslots = generateTimeslots(lcdStartDate, lcdEndDate,
                meetingDuration, lastDailyMeetingTime);

        LinkedHashMap<LocalDateTime, Integer> attendance = new LinkedHashMap<>();
        for (LocalDateTime timeslot : meetingTimeslots) {
            attendance.put(timeslot, 0);
        }
        for (CalendarWrapper memberCalendar : internalList) {
            List<LocalDateTime> memberAvailability =
                    generateMemberAvailibility(memberCalendar.getAvailabilityDuringPeriod(searchPeriod),
                            meetingDuration,
                            lastDailyMeetingTime);

            addMemberAvailability(attendance, memberAvailability);
        }
        return attendance;
    }

    //Generate HashMap of LocalDateTime and Integer with value 0
    //Get PeriodList of freeTimes of each Calendar
    //Convert from ical4j.DateTime to time.LocalDateTime
    //Normalise PeriodList of each calendar
    //For each period, extract smaller period for meeting times and increment HashMap

    ////Sample Duration
//        LocalDateTime startDate = LocalDateTime.parse("20191028T000000Z");
//        LocalDateTime endDate = LocalDateTime.parse("20191101T170000Z");
    public List<LocalDateTime> findMeetingTime(LocalDateTime startDate, LocalDateTime endDate, Duration duration) {
        net.fortuna.ical4j.model.DateTime ical4jStartDate = new DateTime(java.sql.Timestamp.valueOf(startDate));
        net.fortuna.ical4j.model.DateTime ical4jEndDate = new DateTime(java.sql.Timestamp.valueOf(endDate));

        net.fortuna.ical4j.model.Period searchPeriod =
                new net.fortuna.ical4j.model.Period(ical4jStartDate, ical4jEndDate);

        LinkedHashMap<LocalDateTime, Integer> timeslotAttendance = generateAttendance(startDate, endDate,
                                                                    duration, searchPeriod);
        List<LocalDateTime> bestTimeSlots = new ArrayList<>();
        int count = 1;
        Iterator<LocalDateTime> timeIterator = timeslotAttendance.keySet().iterator();
        while (timeIterator.hasNext()) {
            LocalDateTime current = timeIterator.next();
            int currentNum = timeslotAttendance.get(current);
            if (currentNum == count) {
                bestTimeSlots.add(current);
            }
        }
        return bestTimeSlots;
    }

    public static void addMemberAvailability(HashMap<LocalDateTime, Integer> attendance,
                                             List<LocalDateTime> memberAvailability) {
        for (LocalDateTime availableSlot : memberAvailability) {
            attendance.put(availableSlot, attendance.get(availableSlot) + 1);
        }
    }


    public static PeriodList filterPeriodsShorterThan(PeriodList periodList, Duration duration) {
        periodList.removeIf(currentPeriod -> {
            Duration periodDuration = Duration.parse(currentPeriod.getDuration().toString());
            return periodDuration.compareTo(duration) < 0;
        });
        return periodList;
    }
}
