package seedu.address.model.day;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import seedu.address.model.activity.Activity;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents the timetable of a {@code Day}.
 * Guarantees: {@code Timetable} is filled with {@code HalfHour}.
 */
public class Timetable {
    private TreeSet<ActivityWithTime> timetable;

    public Timetable(List<ActivityWithTime> activities) {
        this.timetable = new TreeSet<>();
        if (activities.size() > 0) {
            Iterator<ActivityWithTime> activitiesIterator = activities.iterator();
            this.timetable.add(activitiesIterator.next());

            while (activitiesIterator.hasNext()) {
                ActivityWithTime toAdd = activitiesIterator.next();
                ActivityWithTime floorActivity = this.timetable.floor(toAdd);
                ActivityWithTime ceilingActivity = this.timetable.ceiling(toAdd);

                if (floorActivity == null) {
                    // check if toAdd's endTime does not overlap with ceilingActivity's startTime
                    if (toAdd.getEndTime().compareTo(ceilingActivity.getStartTime()) <= 0) {
                        this.timetable.add(toAdd);
                    }
                } else if (ceilingActivity == null) {
                    // check if toAdd's startTime does not overlap with ceilingActivity's endTime
                    if (toAdd.getStartTime().compareTo(floorActivity.getEndTime()) >= 0) {
                        this.timetable.add(toAdd);
                    }
                } else {
                    if (toAdd.getStartTime().compareTo(floorActivity.getEndTime()) >= 0
                        && toAdd.getEndTime().compareTo(ceilingActivity.getStartTime()) <= 0) {
                        this.timetable.add(toAdd);
                    }
                }
            }
        }
    }

    public Optional<Activity> getActivityAtTime(Date time) {
        ActivityWithTime timeToSearch = new ActivityWithTime(createEmptyActivity(), time, time);
        ActivityWithTime floorActivity = this.timetable.floor(timeToSearch);
        if (floorActivity == null) {
            return Optional.empty();
        } else if (floorActivity.getEndTime().compareTo(time) >= 0) {
            return Optional.of(floorActivity.getActivity());
        } else {
            return Optional.empty();
        }
    }

    public List<ActivityWithTime> getActivitiesWithTime() {
        return new ArrayList<>(this.timetable);
    }

    private Activity createEmptyActivity() {
        return new Activity(new Name("activityStub"), new Address("addressStub"), null, new HashSet<Tag>());
    }

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
        return checkIfTwoListAreEqual(thisActivities, otherActivities);
    }

    private boolean checkIfTwoListAreEqual(List<ActivityWithTime> list1, List<ActivityWithTime> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        Iterator<ActivityWithTime> list1Iterator = list1.listIterator();
        Iterator<ActivityWithTime> list2Iterator = list2.listIterator();
        while (list1Iterator.hasNext()) {
            ActivityWithTime list1Next = list1Iterator.next();
            ActivityWithTime list2Next = list2Iterator.next();
            if (!list1Next.equals(list2Next)) {
                return false;
            }
        }
        return true;
    }
}
