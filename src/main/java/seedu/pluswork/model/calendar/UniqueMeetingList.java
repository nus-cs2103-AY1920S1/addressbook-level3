package seedu.pluswork.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pluswork.model.calendar.exceptions.DuplicateMeetingException;
import seedu.pluswork.model.calendar.exceptions.MeetingNotFoundException;
import seedu.pluswork.model.member.MemberName;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Task#isSameTask(Task)}. As such, adding and updating of
 * persons uses Task#isSameTask(Task) for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a task uses Task#equals(Object) so
 * as to ensure that the task with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Task#isSameTask(Task)
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();
    private final ObservableList<Meeting> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public void updateMemberRemoved(MemberName removedMember) {
        List<Meeting> tmpList = internalList.stream()
                .map(meeting -> meeting.updateMemberRemoved(removedMember))
                .filter(meeting -> meeting.getMemberNameList().size() > 0)
                .collect(Collectors.toList());
        internalList.setAll(tmpList);
    }

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMeeting);
    }

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    public void add(Meeting toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireAllNonNull(target, editedMeeting);

        int index = getIndexOf(target);
        if (!target.isSameMeeting(editedMeeting) && contains(editedMeeting)) {
            throw new DuplicateMeetingException();
        }

        internalList.set(index, editedMeeting);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(Meeting toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MeetingNotFoundException();
        }
    }

    /**
     * Clears the task list of all tasks.
     */
    public void clearAll() {
        internalList.clear();
    }

    public Stream<Meeting> toStream() {
        return internalList.stream();
    }

    public void setMeetings(UniqueMeetingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setMeetings(List<Meeting> meetings) {
        requireAllNonNull(meetings);
        if (!meetingsAreUnique(meetings)) {
            throw new DuplicateMeetingException();
        }

        internalList.setAll(meetings);
    }

    public int getIndexOf(Meeting target) {
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MeetingNotFoundException();
        }
        return index;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meeting> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Meeting> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                && internalList.equals(((UniqueMeetingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tasks} contains only unique tasks.
     */
    private boolean meetingsAreUnique(List<Meeting> meetings) {
        for (int i = 0; i < meetings.size() - 1; i++) {
            for (int j = i + 1; j < meetings.size(); j++) {
                if (meetings.get(i).isSameMeeting(meetings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
