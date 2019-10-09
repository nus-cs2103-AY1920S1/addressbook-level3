package seedu.module.model;

import javafx.collections.ObservableList;
import seedu.module.model.module.Module;

/**
 * Unmodifiable view of an module book
 */
public interface ReadOnlyModuleBook {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

}
