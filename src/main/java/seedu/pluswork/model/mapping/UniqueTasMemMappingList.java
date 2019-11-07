package seedu.pluswork.model.mapping;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pluswork.model.mapping.exceptions.DuplicateMappingException;
import seedu.pluswork.model.mapping.exceptions.MappingNotFoundException;
import seedu.pluswork.model.task.Task;

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
public class UniqueTasMemMappingList implements Iterable<TasMemMapping> {

    private final ObservableList<TasMemMapping> internalList = FXCollections.observableArrayList();
    private final ObservableList<TasMemMapping> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public ObservableList<Integer> getMembersMappedToTask(int taskIndex) {
        ObservableList<Integer> result = FXCollections.observableArrayList();
        for (TasMemMapping mapping : internalList) {
            if (mapping.hasTask(taskIndex)) {
                result.add(mapping.getMemberIndex());
            }
        }
        return result;
    }

    public ObservableList<Integer> getTasksMappedToMember(int memberIndex) {
        ObservableList<Integer> result = FXCollections.observableArrayList();
        for (TasMemMapping mapping : internalList) {
            if (mapping.hasMember(memberIndex)) {
                result.add(mapping.getTaskIndex());
            }
        }
        return result;
    }

    /**
     * returns a hashMap of members by tasks
     */
    public HashMap<Integer, ObservableList<Integer>> listMemberByTask() {
        HashMap<Integer, ObservableList<Integer>> result = new HashMap<>();
        for (TasMemMapping mapping : internalList) {
            int currentTaskIndex = mapping.getTaskIndex();
            if (result.get(currentTaskIndex) == null) {
                result.put(currentTaskIndex, FXCollections.observableArrayList());
            }
            result.get(currentTaskIndex).add(mapping.getMemberIndex());
        }
        return result;
    }

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */

    public void add(TasMemMapping toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMappingException();
        }
        internalList.add(toAdd);
    }

    public void remove(TasMemMapping toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MappingNotFoundException();
        }
    }

    public boolean contains(TasMemMapping toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMapping);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setMapping(TasMemMapping target, TasMemMapping editedMapping) {
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

    public void setMappings(UniqueTasMemMappingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setMappings(List<TasMemMapping> mappings) {
        requireAllNonNull(mappings);
        if (!mappingsAreUnique(mappings)) {
            throw new DuplicateMappingException();
        }

        internalList.setAll(mappings);
    }

    public void updateTaskRemoved(int index) {
        ListIterator<TasMemMapping> iterator = iterator();
        while (iterator.hasNext()) {
            TasMemMapping mapping = iterator.next();
            int mappingIndex = mapping.getTaskIndex();
            if (mappingIndex == index) {
                iterator.remove();
            } else if (mappingIndex > index) {
                TasMemMapping updatedMapping = new TasMemMapping(mappingIndex - 1, mapping.getMemberIndex());
                iterator.remove();
                iterator.add(updatedMapping);
            }
        }
    }

    public void updateMemberRemoved(int index) {
        ListIterator<TasMemMapping> iterator = iterator();
        while (iterator.hasNext()) {
            TasMemMapping mapping = iterator.next();
            int mappingIndex = mapping.getMemberIndex();
            if (mappingIndex == index) {
                iterator.remove();
            } else if (mappingIndex > index) {
                TasMemMapping updatedMapping = new TasMemMapping(mapping.getTaskIndex(), mappingIndex - 1);
                iterator.remove();
                iterator.add(updatedMapping);
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TasMemMapping> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public ListIterator<TasMemMapping> iterator() {
        return internalList.listIterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTasMemMappingList // instanceof handles nulls
                && internalList.equals(((UniqueTasMemMappingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tasks} contains only unique tasks.
     */
    private boolean mappingsAreUnique(List<TasMemMapping> mappings) {
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
