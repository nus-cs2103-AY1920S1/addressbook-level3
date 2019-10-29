package seedu.address.model.mapping;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.mapping.exceptions.MappingNotFoundException;
import seedu.address.model.mapping.exceptions.IllegalMappingStateException;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;

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
public class UniqueMappingManager {

    private final UniqueInvMemMappingList invMemMappingList;
    private final UniqueInvTasMappingList invTasMappingList;
    private final UniqueTasMemMappingList tasMemMappingList;

    public UniqueMappingManager() {
        invMemMappingList = new UniqueInvMemMappingList();
        invTasMappingList = new UniqueInvTasMappingList();
        tasMemMappingList = new UniqueTasMemMappingList();
    }

    // ================ InvMem methods ==============================

    public void add(Mapping toAdd) {
        requireNonNull(toAdd);
        if (toAdd instanceof InvMemMapping) {
            InvMemMapping convertedToAdd = (InvMemMapping) toAdd;
            invMemMappingList.add(convertedToAdd);
        } else if (toAdd instanceof InvTasMapping) {
            InvTasMapping convertedToAdd = (InvTasMapping) toAdd;
            invTasMappingList.add(convertedToAdd);
        } else if (toAdd instanceof TasMemMapping) {
            TasMemMapping convertedToAdd = (TasMemMapping) toAdd;
            tasMemMappingList.add(convertedToAdd);
        } else {
            throw new IllegalMappingStateException();
        }
    }

    public void remove(Mapping toRemove) {
        requireNonNull(toRemove);
        if (toRemove instanceof InvMemMapping) {
            InvMemMapping convertedToRemove = (InvMemMapping) toRemove;
            invMemMappingList.remove(convertedToRemove);
        } else if (toRemove instanceof InvTasMapping) {
            InvTasMapping convertedToRemove = (InvTasMapping) toRemove;
            invTasMappingList.remove(convertedToRemove);
        } else if (toRemove instanceof TasMemMapping) {
            TasMemMapping convertedToRemove = (TasMemMapping) toRemove;
            tasMemMappingList.remove(convertedToRemove);
        } else {
            throw new IllegalMappingStateException();
        }
    }

    public boolean contains(Mapping toCheck) {
        requireNonNull(toCheck);
        if (toCheck instanceof InvMemMapping) {
            InvMemMapping convertedToCheck = (InvMemMapping) toCheck;
            return invMemMappingList.contains(convertedToCheck);
        } else if (toCheck instanceof InvTasMapping) {
            InvTasMapping convertedToCheck = (InvTasMapping) toCheck;
            return invTasMappingList.contains(convertedToCheck);
        } else if (toCheck instanceof TasMemMapping) {
            TasMemMapping convertedToCheck = (TasMemMapping) toCheck;
            return tasMemMappingList.contains(convertedToCheck);
        } else {
            throw new IllegalMappingStateException();
        }
    }

    public void setMapping(Mapping target, Mapping editedMapping) {
        requireAllNonNull(target, editedMapping);
        if (target instanceof InvMemMapping && editedMapping instanceof InvMemMapping) {
            InvMemMapping convertedTarget = (InvMemMapping) target;
            InvMemMapping convertedEditedMapping = (InvMemMapping) editedMapping;
            invMemMappingList.setMapping(convertedTarget, convertedEditedMapping);
        } else if (target instanceof InvTasMapping && editedMapping instanceof InvTasMapping) {
            InvTasMapping convertedTarget = (InvTasMapping) target;
            InvTasMapping convertedEditedMapping = (InvTasMapping) editedMapping;
            invTasMappingList.setMapping(convertedTarget, convertedEditedMapping);
        } else if (target instanceof TasMemMapping && editedMapping instanceof TasMemMapping) {
            TasMemMapping convertedTarget = (TasMemMapping) target;
            TasMemMapping convertedEditedMapping = (TasMemMapping) editedMapping;
            tasMemMappingList.setMapping(convertedTarget, convertedEditedMapping);
        } else {
            throw new IllegalMappingStateException();
        }
    }

//    public void add(InvMemMapping toAdd) {
//        requireNonNull(toAdd);
//        invMemMappingList.add(toAdd);
//    }
//
//    public void remove(InvMemMapping toRemove) {
//        requireNonNull(toRemove);
//        invMemMappingList.remove(toRemove);
//    }
//
//    public boolean contains(InvMemMapping toCheck) {
//        requireNonNull(toCheck);
//        return invMemMappingList.contains(toCheck);
//    }
//
//    public void setMapping(InvMemMapping target, InvMemMapping editedMapping) {
//        requireAllNonNull(target, editedMapping);
//        invMemMappingList.setMapping(target, editedMapping);
//    }

    public void setMappings(UniqueInvMemMappingList replacement) {
        requireNonNull(replacement);
        invMemMappingList.setMappings(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<InvMemMapping> getUnmodifiableObservableInvMemList() {
        return invMemMappingList.asUnmodifiableObservableList();
    }

    // ================ InvTas methods ==============================

//    public void add(InvTasMapping toAdd) {
//        requireNonNull(toAdd);
//        invTasMappingList.add(toAdd);
//    }
//
//    public void remove(InvTasMapping toRemove) {
//        requireNonNull(toRemove);
//        invTasMappingList.remove(toRemove);
//    }
//
//    public boolean contains(InvTasMapping toCheck) {
//        requireNonNull(toCheck);
//        return invTasMappingList.contains(toCheck);
//    }
//
//    public void setMapping(InvTasMapping target, InvTasMapping editedMapping) {
//        requireAllNonNull(target, editedMapping);
//        invTasMappingList.setMapping(target, editedMapping);
//    }

    public void setMappings(UniqueInvTasMappingList replacement) {
        requireNonNull(replacement);
        invTasMappingList.setMappings(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<InvTasMapping> getUnmodifiableObservableInvTasList() {
        return invTasMappingList.asUnmodifiableObservableList();
    }


    // ================ TasMem methods ==============================

    /**
     *returns a hashMap of members by tasks
     */
    public HashMap<Integer, ObservableList<Integer>> listMemberByTask() {
        return tasMemMappingList.listMemberByTask();
    }

    public ObservableList<Integer> getMembersMappedToTask(int taskIndex) {
        return tasMemMappingList.getMembersMappedToTask(taskIndex);
    }

    public ObservableList<Integer> getTasksMappedToMember(int memberIndex) {
        return tasMemMappingList.getTasksMappedToMember(memberIndex);
    }

//    public void add(TasMemMapping toAdd) {
//        requireNonNull(toAdd);
//        tasMemMappingList.add(toAdd);
//    }
//
//    public void remove(TasMemMapping toRemove) {
//        requireNonNull(toRemove);
//        tasMemMappingList.remove(toRemove);
//    }
//
//    public boolean contains(TasMemMapping toCheck) {
//        requireNonNull(toCheck);
//        return tasMemMappingList.contains(toCheck);
//    }
//
//    public void setMapping(TasMemMapping target, TasMemMapping editedMapping) {
//        requireAllNonNull(target, editedMapping);
//        tasMemMappingList.setMapping(target, editedMapping);
//    }

    public void setMappings(UniqueTasMemMappingList replacement) {
        requireNonNull(replacement);
        tasMemMappingList.setMappings(replacement);
    }

    public void setInvMemMappings(List<InvMemMapping> replacement) {
        requireNonNull(replacement);
        invMemMappingList.setMappings(replacement);
    }

    public void setInvTasMappings(List<InvTasMapping> replacement) {
        requireNonNull(replacement);
        invTasMappingList.setMappings(replacement);
    }

    public void setTasMemMappings(List<TasMemMapping> replacement) {
        requireNonNull(replacement);
        tasMemMappingList.setMappings(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TasMemMapping> getUnmodifiableObservableTasMemList() {
        return tasMemMappingList.asUnmodifiableObservableList();
    }

    // ==============================================

    public ObservableList<Mapping> getUnmodifiableObserableList() {
        ObservableList<Mapping> result = FXCollections.observableArrayList();
        result.addAll(invMemMappingList.asUnmodifiableObservableList());
        result.addAll(invTasMappingList.asUnmodifiableObservableList());
        result.addAll(tasMemMappingList.asUnmodifiableObservableList());
        return result;
    }

    public void updateTaskRemoved(int index) {
        invTasMappingList.updateTaskRemoved(index);
        tasMemMappingList.updateTaskRemoved(index);
    }

    public void updateMemberRemoved(int index) {
        invMemMappingList.updateMemberRemoved(index);
        tasMemMappingList.updateMemberRemoved(index);
    }

    public void updateInventoryRemoved(int index) {
        invMemMappingList.updateInventoryRemoved(index);
        invTasMappingList.updateInventoryRemoved(index);
    }
}
