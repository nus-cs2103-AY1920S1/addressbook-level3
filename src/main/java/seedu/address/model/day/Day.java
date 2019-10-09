package seedu.address.model.day;

import seedu.address.commons.core.time.DurationInHalfHour;
import seedu.address.commons.core.index.Index;
import seedu.address.model.day.exceptions.ActivityNotFoundInDayException;
import seedu.address.model.day.exceptions.TimeOutOfBoundsException;
import seedu.address.model.day.exceptions.TimeSlotNotOccupiedException;
import seedu.address.model.day.exceptions.TimeSlotOccupiedException;

import java.util.ArrayList;
import java.util.List;

public class Day {
    private HalfHour[] timeSlots;
    private static final int NUMBER_OF_HALF_HOUR_IN_A_DAY = 48;

    public Day() {
        timeSlots = new HalfHour[48];
        for (int i = 0; i < NUMBER_OF_HALF_HOUR_IN_A_DAY; i++) {
            timeSlots[i] = new HalfHour();
        }
    }

    public void scheduleActivity(ActivityStub activity, Index firstHalfHourIndex, DurationInHalfHour duration)
            throws TimeSlotOccupiedException, TimeOutOfBoundsException {
        Index lastHalfHourIndex = Index.fromZeroBased(
                firstHalfHourIndex.getZeroBased() + duration.getNumberOfHalfHour() - 1
        );

        checkIfTimeRangeIsOccupied(firstHalfHourIndex, lastHalfHourIndex);
        checkIfTimeIsOutOfBounds(lastHalfHourIndex);

        for (int i = firstHalfHourIndex.getZeroBased(); i <= lastHalfHourIndex.getZeroBased(); i++) {
            timeSlots[i].setActivity(activity);
        }
    }

    public void unscheduleActivity(ActivityStub activity) throws ActivityNotFoundInDayException {
        int removeCounter = 0;
        for (int i = 0; i < NUMBER_OF_HALF_HOUR_IN_A_DAY; i++) {
            HalfHour currTime = timeSlots[i];
            if (currTime.getActivity().equals(activity)) {
                currTime.clearActivity();
                removeCounter++;
            }
        }
        if (removeCounter < 1) {
            throw new ActivityNotFoundInDayException();
        }
    }

    public void unscheduleActivity(Index halfHourIndex) throws TimeSlotNotOccupiedException {
        HalfHour currHalfHour = timeSlots[halfHourIndex.getZeroBased()];
        if (!currHalfHour.getIsOccupied()) {
            throw new TimeSlotNotOccupiedException();
        }
        ActivityStub activityToRemove = currHalfHour.getActivity();
        int offset = 0;
        while(currHalfHour.getIsOccupied()) {
            currHalfHour.clearActivity();
            offset++;
            currHalfHour = timeSlots[halfHourIndex.getZeroBased() + offset];
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

    private void checkIfTimeRangeIsOccupied(Index startIndex, Index endIndex) throws TimeSlotOccupiedException{
        for (int i = startIndex.getZeroBased(); i <= endIndex.getZeroBased(); i++) {
            if (timeSlots[i].getIsOccupied()) {
                throw new TimeSlotOccupiedException();
            }
        }
    }

    private void checkIfTimeIsOutOfBounds(Index index) {
        int indexInInt = index.getZeroBased();
        if (indexInInt > NUMBER_OF_HALF_HOUR_IN_A_DAY || indexInInt < 0) {
            throw new TimeOutOfBoundsException();
        }
    }
}
