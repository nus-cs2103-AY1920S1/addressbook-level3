package seedu.address.model.day;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.model.activity.Activity;
import seedu.address.model.day.exceptions.TimeSlotUnavailableException;
import seedu.address.model.day.time.TimeInHalfHour;

/**
 * Represents the timetable of a {@code Day}.
 * Guarantees: {@code Timetable} is filled with {@code HalfHour}.
 */
public class Timetable {
    protected static final int NUMBER_OF_HALF_HOUR_IN_A_DAY = 48;
    private TimeSlot[] timetable = new TimeSlot[NUMBER_OF_HALF_HOUR_IN_A_DAY];

    public Timetable(List<ActivityWithTime> activities) throws TimeSlotUnavailableException {
        for (ActivityWithTime a : activities) {
            Activity activity = a.getActivity();
            Index startIndex = convertTimeToIndex(a.getTime());
            Index endIndex = Index.fromZeroBased(
                    startIndex.getZeroBased() + a.getDuration().getNumberOfHalfHour()
            );
            addActivityToIndexRange(activity, startIndex, endIndex);
        }

        // fill up remaining time slots with empty half hours
        for (int i = 0; i < NUMBER_OF_HALF_HOUR_IN_A_DAY; i++) {
            if (timetable[i] == null) {
                timetable[i] = new TimeSlot(null);
            }
        }
    }

    public Optional<Activity> getActivityAtIndex(Index index) {
        return timetable[index.getZeroBased()].getActivity();
    }

    public boolean getIsAvailableAtIndex(Index index) {
        return timetable[index.getZeroBased()].isAvailable();
    }

    /**
     * Adds an activity to timetable.
     *
     * @param start first index to add activity
     * @param end last index to add the activity
     */
    private void addActivityToIndexRange(Activity activity, Index start, Index end)
            throws TimeSlotUnavailableException {
        for (int i = start.getZeroBased(); i <= end.getZeroBased(); i++) {
            if (timetable[i] != null) {
                throw new TimeSlotUnavailableException();
            }
            timetable[i] = new TimeSlot(activity);
        }
    }

    private Index convertTimeToIndex(TimeInHalfHour time) {
        return Index.fromZeroBased(
                time.getHour() * 2 + time.getMinutes() / 30
        );
    }
}
