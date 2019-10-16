package seedu.address.model.mapping;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.mapping.exceptions.MappingNotFoundException;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.model.member.Member;

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
public class UniqueMappingList implements Iterable<Mapping> {

    private final ObservableList<Mapping> internalList = FXCollections.observableArrayList();
    private final ObservableList<Mapping> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public ObservableList<Member> getMappedMembers(Task task) {
        requireNonNull(task);
        ObservableList<Member> result = FXCollections.observableArrayList();
        for (Mapping mapping : internalList) {
            if (mapping.hasTask(task)) {
                result.add(mapping.getMember());
            }
        }
        return result;
    }

    public ObservableList<Task> getMappedTasks(Member member) {
        requireNonNull(member);
        ObservableList<Task> result = FXCollections.observableArrayList();
        for (Mapping mapping : internalList) {
            if (mapping.hasMember(member)) {
                result.add(mapping.getTask());
            }
        }
        return result;
    }

    public HashMap<Task, ObservableList<Member>> listMemberByTask() {
        HashMap<Task, ObservableList<Member>> result = new HashMap<>();
        for (Mapping mapping : internalList) {
            Task currentTask = mapping.getTask();
            if (result.get(currentTask) == null) {
                result.put(currentTask, FXCollections.observableArrayList());
            }
            result.get(currentTask).add(mapping.getMember());
        }
        return result;
    }

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Mapping toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMapping);
    }

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    public void add(Mapping toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMappingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setMapping(Mapping target, Mapping editedMapping) {
        requireAllNonNull(target, editedMapping);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MappingNotFoundException();
        }

        if (!target.isSameMapping(editedMapping) && contains(editedMapping)) {
            throw new DuplicateMappingException();
        }

        internalList.set(index, editedMapping);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(Mapping toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MappingNotFoundException();
        }
    }

    public void setMappings(UniqueMappingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setMappings(List<Mapping> mappings) {
        requireAllNonNull(mappings);
        if (!mappingsAreUnique(mappings)) {
            throw new DuplicateMappingException();
        }

        internalList.setAll(mappings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Mapping> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Mapping> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMappingList // instanceof handles nulls
                        && internalList.equals(((UniqueMappingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tasks} contains only unique tasks.
     */
    private boolean mappingsAreUnique(List<Mapping> mappings) {
        for (int i = 0; i < mappings.size() - 1; i++) {
            for (int j = i + 1; j < mappings.size(); j++) {
                if (mappings.get(i).isSameMapping(mappings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
