package seedu.module.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.module.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.module.model.module.exceptions.DuplicateModuleException;
import seedu.module.model.module.exceptions.ModuleNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Module#isSameModule(Module)}. As such, adding and updating of
 * modules uses Module#isSameModule(Module) for equality so as to ensure that the module being added or updated is
 * unique in terms of identity in the UniqueModuleList. However, the removal of a module uses Module#equals(Object) so
 * as to ensure that the Module with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see TrackedModule#isSameModule(TrackedModule)
 */
public class UniqueModuleList implements Iterable<TrackedModule> {

    private final ObservableList<TrackedModule> internalList = FXCollections.observableArrayList();
    private final ObservableList<TrackedModule> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Module as the given argument.
     */
    public boolean contains(TrackedModule toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Adds a Module to the list.
     * The Module must not already exist in the list.
     */
    public void add(TrackedModule toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent module from the list.
     * The modules must exist in the list.
     */
    public void remove(TrackedModule toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<TrackedModule> trackedModules) {
        requireAllNonNull(trackedModules);
        if (!modulesAreUnique(trackedModules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(trackedModules);
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another existing module in the list.
     */
    public void setModule(TrackedModule target, TrackedModule editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        internalList.set(index, editedModule);
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TrackedModule> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<TrackedModule> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                && internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     */
    private boolean modulesAreUnique(List<TrackedModule> trackedModules) {
        for (int i = 0; i < trackedModules.size() - 1; i++) {
            for (int j = i + 1; j < trackedModules.size(); j++) {
                if (trackedModules.get(i).isSameModule(trackedModules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
