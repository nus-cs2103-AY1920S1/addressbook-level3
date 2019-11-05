package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;

/**
 * Wrapper for all the activities stored by this application.
 */
public class ActivityBook implements ReadOnlyActivityBook {

    private final ObservableList<Activity> activityList = FXCollections.observableArrayList();
    private final ObservableList<Activity> unmodifiableActivityList =
            FXCollections.unmodifiableObservableList(activityList);

    public ActivityBook() { }

    /**
     * Creates an ActivityBook using the Activities in the {@code previousActivityBook}
     */
    public ActivityBook(ActivityBook previousActivityBook) {
        activityList.addAll(previousActivityBook.getActivityList());
    }

    // ================ List overwrite operations ================

    /**
     * Returns true if an activity with the same primary key as {@code primaryKey} exists in the activity book.
     */
    public boolean hasPrimaryKey(int primaryKey) {
        for (Activity activity : activityList) {
            if (activity.getPrimaryKey() == primaryKey) {
                return true;
            }
        }
        return false;
    }

    /**
     * Replaces the contents of the activity list with {@code activityList}.
     */
    public void setActivities(List<Activity> activities) {
        activityList.clear();
        activityList.addAll(activities);
    }

    /**
     * Resets the existing data of this {@code ActivityBook} with {@code newData}.
     */
    public void resetData(ActivityBook newData) {
        requireNonNull(newData);
        setActivities(newData.getActivityList());
    }

    // ================ Activity-level operations ================

    /**
     * Adds an activity to the activity book.
     */
    public void addActivity(Activity a) {
        requireNonNull(a);
        activityList.add(a);
    }

    /**
     * Removes {@code key} from this {@code ActivityBook}.
     * {@code key} must exist in the activity book.
     */
    public void removeActivity(Activity key) {
        requireNonNull(key);
        if (!activityList.remove(key)) {
            throw new ActivityNotFoundException();
        }
    }

    /**
     * Replaces the given activity {@code target} in the list with {@code editedActivity}.
     * {@code target} must exist in the activity book.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireAllNonNull(target, editedActivity);

        int index = activityList.indexOf(target);
        if (index == -1) {
            throw new ActivityNotFoundException();
        }

        activityList.set(index, editedActivity);
    }

    // ================ Utility methods ================

    @Override
    public String toString() {
        return activityList.size() + " activities";
    }

    /**
     * Returns the backing list of activities as an unmodifiable {@code ObservableList}.
     */
    @Override
    public ObservableList<Activity> getActivityList() {
        return unmodifiableActivityList;
    }

    @Override
    public boolean equals(Object other) {
        if (other != this) {
            return other instanceof ActivityBook
                    && activityList.equals(((ActivityBook) other).activityList);
        }
        return true;
    }

    /**
     * Checks whether the person with ID is present in any activity.
     * @param personId Id of the person to check.
     * @return True if person exists, false otherwise.
     */
    public boolean hasPerson(Integer personId) {
        for (Activity activity : activityList) {
            if (activity.hasPerson(personId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return activityList.hashCode();
    }

}
