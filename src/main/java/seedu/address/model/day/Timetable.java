package seedu.address.model.day;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.time.TimeInHalfHour;
import seedu.address.model.day.exceptions.TimeSlotOccupiedException;

import java.util.ArrayList;
import java.util.List;

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
        for (int i = 0; i < NUMBER_OF_HALF_HOUR_IN_A_DAY; i ++) {
            if (timeSlots[i] == null) {
                timeSlots[i] = new HalfHour();
            }
        }
    }

    public List<Index> searchForActivity(ActivityStub activity) {
        boolean isDiffActivity = true;
        List<Index> indexList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_HALF_HOUR_IN_A_DAY; i++) {
            HalfHour currTime = timeSlots[i];
            if (currTime.getActivity().equals(activity)) {
                if (isDiffActivity) {
                    isDiffActivity = false;
                    indexList.add(Index.fromZeroBased(i));
                }
            } else {
                isDiffActivity = true;
            }
        }
        return indexList;
    }

    public boolean checkIfTimeRangeIsOccupied(Index startIndex, Index endIndex) {
        for (int i = startIndex.getZeroBased(); i <= endIndex.getZeroBased(); i++) {
            if (timeSlots[i].getIsOccupied()) {
                return true;
            }
        }
        return false;
    }

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
                time.getHour() * 2 + time.getMins() / 30
        );
    }
}
