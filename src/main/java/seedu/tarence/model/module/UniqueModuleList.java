package seedu.tarence.model.module;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.model.module.exceptions.DuplicateModuleException;
import seedu.tarence.model.module.exceptions.ModuleNotFoundException;

/**
 * Represents a list of modules.
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void add(Module newModule) {
        requireNonNull(newModule);
        if (contains(newModule)) {
            throw new DuplicateModuleException();
        }
        internalList.add(newModule);
    }

    /**
     * Removes the equivalent module from the list.
     * The person must exist in the list.
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Returns the {@code Module} of the given {@code ModCode}, or {@code Optional<Empty>} if it does not exist.
     */
    public Optional<Module> getModuleByCode(ModCode modCode) {
        requireNonNull(modCode);
        for (Module module : internalList) {
            if (module.getModCode().equals(modCode)) {
                return Optional.of(module);
            }
        }
        return Optional.empty();
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

}
