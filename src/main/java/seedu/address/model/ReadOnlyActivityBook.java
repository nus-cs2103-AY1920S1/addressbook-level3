package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;

/**
 * Unmodifiable view of an activity book
 */
public interface ReadOnlyActivityBook {

    /**
     * Returns an unmodifiable view of the list of activities.
     * This list will not contain any activities with duplicate primary keys.
     */
    ObservableList<Activity> getActivityList();

}
