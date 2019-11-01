package seedu.planner.model;

import javafx.collections.ObservableList;
import seedu.planner.model.activity.Activity;

/**
 * Unmodifiable view of an Activity List
 */
public interface ReadOnlyActivity {

    /**
     * Returns an unmodifiable view of the activities list.
     * This list will not contain any duplicate activities.
     */
    ObservableList<Activity> getActivityList();

}
