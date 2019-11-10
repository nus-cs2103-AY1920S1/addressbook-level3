package seedu.planner.model.activity;

import java.util.Objects;

//@@author oscarsu97

/**
 * Represents the number of times the Activity appears in the Timetable.
 */
public class ActivityWithCount implements Comparable<ActivityWithCount> {
    private final Activity activity;
    private final long count;

    public ActivityWithCount(Activity activity, long count) {
        this.activity = activity;
        this.count = count;
    }

    public Activity getActivity() {
        return activity;
    }

    public long getCount() {
        return count;
    }

    @Override
    public int compareTo(ActivityWithCount o) {
        return (int) (count - o.count);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ActivityWithCount
                && activity.equals((((ActivityWithCount) other).activity))
                && count == ((ActivityWithCount) other).count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, count);
    }
}
