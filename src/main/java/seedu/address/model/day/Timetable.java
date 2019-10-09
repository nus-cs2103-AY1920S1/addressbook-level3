package seedu.address.model.day;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.day.time.TimeInHalfHour;
import seedu.address.model.day.exceptions.TimeSlotOccupiedException;

/**
 * Represents the timetable of a {@code Day}.
 * Guarantees: {@code Timetable} is filled with {@code HalfHour}.
 */
public class Timetable {
    private static final int NUMBER_OF_HALF_HOUR_IN_A_DAY = 48;
    private HalfHour[] timeSlots;

    public Timetable() {
        timeSlots = new HalfHour[48];
        for (int i = 0; i < NUMBER_OF_HALF_HOUR_IN_A_DAY; i++) {
            timeSlots[i] = new HalfHour();
        }
    }

    public Timetable(List<ActivityInTimeRange> activities) throws TimeSlotOccupiedException {
        timeSlots = new HalfHour[48];
        for (ActivityInTimeRange a : activities) {
            ActivityStub activity = a.getActivity();
            Index startIndex = convertTimeToIndex(a.getTime());
            Index endIndex = Index.fromZeroBased(
                    startIndex.getZeroBased() + a.getDuration().getNumberOfHalfHour() - 1
            );
            addActivityToIndexRange(activity, startIndex, endIndex);
        }

        // fill up remaining time slots with empty half hours
        for (int i = 0; i < NUMBER_OF_HALF_HOUR_IN_A_DAY; i++) {
            if (timeSlots[i] == null) {
                timeSlots[i] = new HalfHour();
            }
        }
    }

    /**
     * Adds an activity to timetable.
     *
     * @param start first index to add activity
     * @param end last index to add the activity
     */
    private void addActivityToIndexRange(ActivityStub activity, Index start, Index end)
            throws TimeSlotOccupiedException {
        for (int i = start.getZeroBased(); i <= end.getZeroBased(); i++) {
            if (timeSlots[i] != null) {
                throw new TimeSlotOccupiedException();
            }
            timeSlots[i] = new HalfHour(activity);
        }
    }

    private Index convertTimeToIndex(TimeInHalfHour time) {
        return Index.fromZeroBased(
                time.getHour() * 2 + time.getMinutes() / 30
        );
    }
}
