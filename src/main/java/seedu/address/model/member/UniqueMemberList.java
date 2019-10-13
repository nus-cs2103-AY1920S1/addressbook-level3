package seedu.address.model.member;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.member.exceptions.DuplicateMemberException;
import seedu.address.model.member.exceptions.MemberNotFoundException;

/**
 * A list of members that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Task#isSameTask(Task)}. As such, adding and updating of
 * persons uses Task#isSameTask(Task) for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a task uses Task#equals(Object) so
 * as to ensure that the task with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Member#isSameMember(Member)
 */
public class UniqueMemberList implements Iterable<Member> {
    private final ObservableList<Member> internalList = FXCollections.observableArrayList();
    private final ObservableList<Member> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Member toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMember);
    }

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    public void add(Member toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMemberException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setMember(Member target, Member editedMember) {
        requireAllNonNull(target, editedMember);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MemberNotFoundException();
        }

        if (!target.isSameMember(editedMember) && contains(editedMember)) {
            throw new DuplicateMemberException();
        }

        internalList.set(index, editedMember);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(Member toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MemberNotFoundException();
        }
    }

    public void setMembers(UniqueMemberList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setMembers(List<Member> members) {
        requireAllNonNull(members);
        if (!tasksAreUnique(members)) {
            throw new DuplicateMemberException();
        }

        internalList.setAll(members);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Member> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Member> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMemberList // instanceof handles nulls
                && internalList.equals(((UniqueMemberList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tasks} contains only unique tasks.
     */
    private boolean tasksAreUnique(List<Member> tasks) {
        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(i).isSameMember(tasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
