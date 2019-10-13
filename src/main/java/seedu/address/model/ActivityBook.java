package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.activity.Activity;

/**
 * Wrapper for all the activities stored by this application.
 */

public class ActivityBook {

    private final ArrayList<Activity> activityList;

    public ActivityBook() {
        activityList = new ArrayList<Activity>();
    }

    /**
     * Creates an ActivityBook using the Activities in the {@code previousActivityBook}
     */
    public ActivityBook(ActivityBook previousActivityBook) {
        activityList = previousActivityBook.getActivityList();
    }

    //// list overwrite operations

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

    //// activity-level operations
    /**
     * Adds an activity to the activity book.
     */
    public void addActivity(Activity a) {
        activityList.add(a);
    }

    /**
     * Removes {@code key} from this {@code ActivityBook}.
     * {@code key} must exist in the activity book.
     */
    public void removeActivity(Activity key) {
        activityList.remove(key);
    }

    /**
     * Replaces the given activity {@code target} in the list with {@code editedActivity}.
     * {@code target} must exist in the activity book.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireNonNull(editedActivity);

        int index = -1;
        for (int z = 0; z < activityList.size(); z++) {
            if (activityList.get(z).equals(target)) {
                index = z;
                break;
            }
        }
        if (index != -1) {
            activityList.set(index, editedActivity);
        }
    }

    //// util methods

    @Override
    public String toString() {
        return activityList.size() + " activities";
    }

    public ArrayList<Activity> getActivityList() {
        return this.activityList;
    }

    @Override
    public boolean equals(Object other) {
        if (other != this) {
            return other instanceof ActivityBook
                    && activityList.equals(((ActivityBook) other).activityList);
        }
        return true;
    }

    @Override
    public int hashCode() {
        return activityList.hashCode();
    }

}
