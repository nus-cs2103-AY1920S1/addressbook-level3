package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.model.itineraryitem.activity.Activity;
import seedu.address.model.itineraryitem.activity.UniqueActivityList;



/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class ActivityManager implements ReadOnlyActivity {
    private final UniqueActivityList activities;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        activities = new UniqueActivityList();
    }

    public ActivityManager() {}

    /**
     * Creates an ActivityManager using the Persons in the {@code toBeCopied}
     */
    public ActivityManager(ReadOnlyActivity toBeCopied) {
        this();
        resetDataActivity(toBeCopied);
    }

    //// For ACTIVITY list overwrite operations

    /**
     * Resets the existing data of this {@code ActivityManager} with {@code newData}.
     */
    public void resetDataActivity(ReadOnlyActivity newData) {
        requireNonNull(newData);

        setActivities(newData.getActivityList());
    }

    /**
     * Replaces the contents of the contacts list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setActivities(List<Activity> activities) {
        this.activities.setActivities(activities);
    }

    //// activity-level operations

    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addActivity(Activity a) {
        activities.add(a);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addActivityAtIndex(Index index, Activity a) {
        activities.addAtIndex(index, a);
    }

    /**
     * Replaces the given contacts {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedPerson} must not be the same as another existing contacts in the address
     * book.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireNonNull(editedActivity);

        activities.setActivity(target, editedActivity);
    }

    /**
     * Removes {@code key} from this {@code ActivityManager}.
     * {@code key} must exist in the address book.
     */
    public void removeActivity(Activity key) {
        activities.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return activities.asUnmodifiableObservableList().size() + " activities,";
    }

    @Override
    public ObservableList<Activity> getActivityList() {
        return activities.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityManager // instanceof handles nulls
                && activities.equals(((ActivityManager) other).activities));
    }

    @Override
    public int hashCode() {
        return activities.hashCode();
    }
}
