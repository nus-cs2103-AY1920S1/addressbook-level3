package seedu.module.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.UniqueModuleList;

/**
 * Wraps all data at the module-book level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class ModuleBook implements ReadOnlyModuleBook {

    private final UniqueModuleList modules;
    private ArchivedModuleList archivedModules;

    public ModuleBook() {
        this.modules = new UniqueModuleList();
        this.archivedModules = new ArchivedModuleList();
    }

    public ModuleBook(ArchivedModuleList archivedModules) {
        this();
        this.archivedModules = archivedModules;
    }

    /**
     * Creates an ModuleBook using the modules in the {@code toBeCopied}
     */
    public ModuleBook(ReadOnlyModuleBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<TrackedModule> trackedModules) {
        this.modules.setModules(trackedModules);
    }

    /**
     * Sets the current ArchivedModuleList with another ArchivedModuleList.
     */
    public void setArchivedModules(ArchivedModuleList archivedModules) {
        this.archivedModules = archivedModules;
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the module book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the module book.
     */
    public void setModule(TrackedModule target, TrackedModule editedModule) {
        requireNonNull(editedModule);
        modules.setModule(target, editedModule);
    }

    /**
     * Resets the existing data of this {@code ModuleBook} with {@code newData}.
     */
    public void resetData(ReadOnlyModuleBook newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
        setArchivedModules(newData.getRawArchivedModuleList());
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the module book.
     */
    public boolean hasModule(TrackedModule trackedModule) {
        requireNonNull(trackedModule);
        return modules.contains(trackedModule);
    }

    /**
     * Adds a module to the module book.
     * The module must not already exist in the module book.
     */
    public void addModule(TrackedModule p) {
        modules.add(p);
    }

    /**
     * Removes {@code key} from this {@code ModuleBook}.
     * {@code key} must exist in the module book.
     */
    public void removeModule(TrackedModule key) {
        modules.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<TrackedModule> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<ArchivedModule> getArchivedModuleList() {
        return archivedModules.asUnmodifiableObservableList();
    }

    @Override
    public ArchivedModuleList getRawArchivedModuleList() {
        return this.archivedModules;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleBook // instanceof handles nulls
                && modules.equals(((ModuleBook) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
