package seedu.planner.model.day;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.planner.commons.core.index.Index;
import seedu.planner.model.activity.Activity;

/**
 * Represents the timetable of a {@code Day}.
 */
public class Timetable {
    private List<ActivityWithTime> timetable;

    //@@author 1nefootstep
    public Timetable() {
        timetable = new ArrayList<>();
    }

    public Timetable(List<ActivityWithTime> activities) {
        timetable = activities;
        timetable.sort(ActivityWithTime::compareTo);
    }

    //@@author 1nefootstep
    public List<ActivityWithTime> getActivitiesWithTime() {
        return new ArrayList<>(this.timetable);
    }

    /**
     * Adds an activity with time to the list.
     */
    public void addActivityWithTime(ActivityWithTime toAdd) {
        this.timetable.add(toAdd);
        timetable.sort(ActivityWithTime::compareTo);
    }

    //@@author 1nefootstep
    public void removeActivityWithIndex(Index toRemove) {
        this.timetable.remove(toRemove.getZeroBased());
    }

    //@@author 1nefootstep
    /**
     * Removes an {@code Activity} from a timetable.
     */
    public void removeActivity(Activity activity) {
        while (timetable.contains(activity)) {
            timetable.remove(timetable.indexOf(activity));
        }
    }

    //@@author 1nefootstep
    public Index getIndex(ActivityWithTime activity) {
        return Index.fromZeroBased(timetable.indexOf(activity));
    }

    //@@author 1nefootstep
    public ActivityWithTime getActivityWithTime(Index index) {
        return timetable.get(index.getZeroBased());
    }

    /**
     * Find the next activity in the list such that there is no overlaps unless the activity is the last in the list.
     */
    public Optional<ActivityWithTime> findNextNoOverlap(Index index) {
        ActivityWithTime currAct = timetable.get(index.getZeroBased());
        for (int i = index.getZeroBased() + 1; i < timetable.size(); i++) {
            ActivityWithTime nextAct = timetable.get(i);
            if (!currAct.isOverlapping(nextAct)) {
                return Optional.of(nextAct);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds all activities that overlaps with the current activity.
     */
    public List<ActivityWithTime> findAllOverlap(ActivityWithTime activity) {
        return timetable.stream().filter(x -> x.isOverlapping(activity)
                && activity.getStartDateTime().compareTo(x.getStartDateTime()) <= 0).collect(Collectors.toList());
    }

    //@@author 1nefootstep
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timetable)) {
            return false;
        }

        List<ActivityWithTime> otherActivities = ((Timetable) other).getActivitiesWithTime();
        List<ActivityWithTime> thisActivities = this.getActivitiesWithTime();
        return thisActivities.equals(otherActivities);
    }
}
