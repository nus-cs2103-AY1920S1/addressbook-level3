package seedu.address.model.cap;

import javafx.collections.ObservableList;
import seedu.address.model.common.Module;


/**
 * Unmodifiable view of an cap log
 */
public interface ReadOnlySemesterList {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

}
