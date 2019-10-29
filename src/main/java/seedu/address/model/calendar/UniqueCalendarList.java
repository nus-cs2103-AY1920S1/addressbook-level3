package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.calendar.exceptions.CalendarNotFoundException;
import seedu.address.model.calendar.exceptions.DuplicateCalendarException;
import seedu.address.model.member.MemberName;

import java.text.ParseException;
import java.time.Duration;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.*;

import org.apache.commons.lang3.time.DateUtils;

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
    private static final TimeZone DEFAULT_TIMEZONE = TimeZoneRegistryFactory
            .getInstance()
            .createRegistry()
            .getTimeZone("Asia/Hong_Kong");
    private static final int END_HOUR = 22;
    private static final int END_MINUTE = 00;

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

    public HashMap<DateTime, Integer> generateTimeslots(DateTime startDate, DateTime endDate, int duration) {
        DateTime dateCount = startDate;
        HashMap<DateTime, Integer> result = new HashMap<>();
        List<DateTime> result2 = new ArrayList<>();
        dateCount = new DateTime(DateUtils.addHours(dateCount, 0));
        while (dateCount.before(endDate)) {
            result.put(dateCount, 0);
            result2.add(dateCount);
            dateCount = new DateTime(DateUtils.addHours(dateCount, 1));
        }
        return result;
    }

    public void getBestTiming() {
        try {
            net.fortuna.ical4j.model.Calendar cal = internalList.get(0).getCalendar();
            DateTime startDate = new DateTime("20191028T000000Z", DEFAULT_TIMEZONE);
            DateTime endDate = new DateTime("20191101T170000Z", DEFAULT_TIMEZONE);
            Period period = new Period(startDate, endDate);
            Duration duration = Duration.ofHours(4);

            PeriodList result = internalList.get(0).getFreeTimeDuringPeriod(period, duration);

            for (Object po : result) {
                System.out.println((Period)po);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
