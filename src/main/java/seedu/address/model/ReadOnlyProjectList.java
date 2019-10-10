package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.project.Project;

/**
 * Unmodifiable view of a projects list.
 */
public interface ReadOnlyProjectList {

    /**
     * Returns an unmodifiable view of the projects list.
     * This list will not contain any duplicate project.
     */
    ObservableList<Project> getProjectList();

}
