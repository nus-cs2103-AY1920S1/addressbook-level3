package seedu.module.model;

import javafx.collections.ObservableList;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;

/**
 * Unmodifiable view of an module book
 */
public interface ReadOnlyModuleBook {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<TrackedModule> getModuleList();

    ObservableList<ArchivedModule> getArchivedModuleList();

    ArchivedModuleList getRawArchivedModuleList();

}
