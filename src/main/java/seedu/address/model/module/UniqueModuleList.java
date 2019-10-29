package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Module#equals(Module)}. As such, adding, updating and
 * removing of modules uses Module#equals(Module) for equality so as to ensure that the module
 * is unique in terms of identity in the UniqueModuleList.
 * <p>
 * Supports a minimal set of list operations.
 */
public class UniqueModuleList implements Iterable<Module>, Cloneable {
    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    private final ObservableList<Module> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    @Override
    public UniqueModuleList clone() {
        UniqueModuleList clone = new UniqueModuleList();
        for (Module module : this) {
            clone.add(module);
        }
        return clone;
    }

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(module -> module.getModuleCode().toString().equals(toCheck));
    }

    /**
     * Wrapper for the {@code contains} method to accept a Module object instead
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds the equivalent module to the list.
     *
     * @param toAdd Module to be added
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent module from the list.
     * The module must exist in the list.
     */
    public void remove(String toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            this.internalList.remove(getModule(toRemove));
        } else {
            throw new ModuleNotFoundException();
        }
    }

    private Module getModule(String moduleCode) {
        for (Module mod : this.internalList) {
            if (mod.getModuleCode().toString().equals(moduleCode)) {
                return mod;
            }
        }
        return null;
    }

    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Module> iterator() {
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
    private boolean modulesAreUnique(List<Module> modules) {
        for (int i = 0; i < modules.size() - 1; i++) {
            for (int j = i + 1; j < modules.size(); j++) {
                if (modules.get(i).equals(modules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getMcCount() {
        try {
            return internalList.stream().map(Module::getMcCount).reduce(Integer::sum).get();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another existing module in the list.
     */
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.equals(editedModule) && !contains(editedModule)) {
            internalList.set(index, editedModule);
        } else {
            throw new DuplicateModuleException();
        }
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * This method is written to facilitate cloning of StudyPlan modules.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} CAN be the same as another existing module in the list.
     */
    public void replace(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }
        internalList.set(index, editedModule);
    }
}
