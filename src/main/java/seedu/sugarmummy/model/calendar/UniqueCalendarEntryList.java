package seedu.sugarmummy.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sugarmummy.model.calendar.exceptions.CalendarEntryNotFoundException;
import seedu.sugarmummy.model.calendar.exceptions.DuplicateCalendarEntryException;

/**
 * A list of calendar entries that enforces uniqueness between its elements and does not allow nulls. A calendar entry
 * is considered unique by comparing using {@code CalendarEntry#isCalendarEntry(CalendarEntry)}. As such, adding and
 * updating of calendar entries uses CalendarEntry#isSameCalendar(CalendarEntry) for equality so as to ensure that the
 * calendar entries being added or updated is unique in terms of identity in the UniqueCalendarEntryList.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see CalendarEntry#isSameCalendarEntry(CalendarEntry)
 */
public class UniqueCalendarEntryList implements Iterable<CalendarEntry> {
    private final ObservableList<CalendarEntry> internalList = FXCollections.observableArrayList();
    private final ObservableList<CalendarEntry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent calendar entry as the given argument.
     */
    public boolean contains(CalendarEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCalendarEntry);
    }

    /**
     * Returns true if any calendar entries in the list covers the given calendar entry.
     */
    public boolean covers(CalendarEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSubsetCalendarEntryOf);
    }

    /**
     * Returns the calendar entry in the list that covers the given calendar entry.
     */
    public CalendarEntry getCalendarEntryCovers(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        assert covers(calendarEntry);
        return internalList.filtered(calendarEntry::isSubsetCalendarEntryOf).get(0);
    }

    /**
     * Returns true if any calendar entries in the list is covered by the given calendar entry.
     */
    public boolean isAnyCoveredBy(CalendarEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(calendarEntry -> calendarEntry.isSubsetCalendarEntryOf(toCheck));
    }

    /**
     * Returns all calendar entries in the list that are covered by the given calendar entry.
     */
    public ObservableList<CalendarEntry> getCalendarEntriesCoveredBy(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return internalList.filtered(calendarEntry1 -> calendarEntry1.isSubsetCalendarEntryOf(calendarEntry));
    }

    /**
     * Returns true if any calendar entries in the list and the given calendar entry overlap.
     */
    public boolean overlaps(CalendarEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::overlaps);
    }

    /**
     * Returns all calendar entries in the list that overlaps the given calendar entry.
     */
    public ObservableList<CalendarEntry> getCalendarEntryOverlaps(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return internalList.filtered(calendarEntry::overlaps);
    }

    /**
     * Returns true if any calendar entries in the list and the given calendar entry conflict.
     */
    public boolean conflicts(CalendarEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::conflicts);
    }

    /**
     * Returns all calendar entries in the list that conflicts the given calendar entry.
     */
    public ObservableList<CalendarEntry> getCalendarEntryConflicts(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return internalList.filtered(calendarEntry::conflicts);
    }

    /**
     * Adds a calendar entry to the list. The calendar entry must not already exist in the list.
     */
    public void add(CalendarEntry toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCalendarEntryException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a list of calendar entries to the list.
     */
    public void addAll(List<CalendarEntry> toAdd) {
        internalList.addAll(toAdd.stream().filter(calendarEntry -> !contains(calendarEntry))
                .collect(Collectors.toList()));
    }

    /**
     * Replaces the calendar entry {@code target} in the list with {@code editedCalendarEntry}. {@code target} must
     * exist in the list. The calendar entry identity of {@code editedCalendarEntry} must not be the same as another
     * existing calendar entry in the list.
     */
    public void setCalendarEntry(CalendarEntry target, CalendarEntry editedCalendarEntry) {
        requireAllNonNull(target, editedCalendarEntry);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CalendarEntryNotFoundException();
        }

        if (!target.isSameCalendarEntry(editedCalendarEntry) && contains(editedCalendarEntry)) {
            throw new DuplicateCalendarEntryException();
        }

        internalList.set(index, editedCalendarEntry);
    }

    /**
     * Removes the equivalent calendar entry from the list. The calendar entry must exist in the list.
     */
    public void remove(CalendarEntry toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CalendarEntryNotFoundException();
        }
    }

    /**
     * Removes the equivalent calendar entries from the list. The calendar entries must exist in the list.
     */
    public void removeAll(List<CalendarEntry> toRemove) {
        internalList.removeAll(toRemove);
    }


    public void setCalendarEntries(UniqueCalendarEntryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code calendarEntries}. {@code calendarEntries} must not contain
     * duplicate calendar entries.
     */
    public void setCalendarEntries(List<CalendarEntry> calendarEntries) {
        requireAllNonNull(calendarEntries);
        if (!calendarEntriesAreUnique(calendarEntries)) {
            throw new DuplicateCalendarEntryException();
        }

        internalList.setAll(calendarEntries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CalendarEntry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CalendarEntry> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCalendarEntryList // instanceof handles nulls
                        && internalList.equals(((UniqueCalendarEntryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code calendarEntries} contains only unique calendar entries.
     */
    private boolean calendarEntriesAreUnique(List<CalendarEntry> calendarEntries) {
        for (int i = 0; i < calendarEntries.size() - 1; i++) {
            for (int j = i + 1; j < calendarEntries.size(); j++) {
                if (calendarEntries.get(i).isSameCalendarEntry(calendarEntries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
