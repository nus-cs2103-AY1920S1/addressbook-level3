package seedu.address.model.day;

import static java.util.Objects.requireNonNull;

public class HalfHour {
    private ActivityStub activityAtThisTime;
    private boolean isOccupied;

    public HalfHour() {
        isOccupied = false;
    }

    public HalfHour(ActivityStub activity) {
        this.activityAtThisTime = activity;
        isOccupied = true;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setActivity(ActivityStub activity) {
        requireNonNull(activity);
        this.activityAtThisTime = activity;
        isOccupied = true;
    }

    public void clearActivity() {
        activityAtThisTime = null;
        isOccupied = false;
    }

    public ActivityStub getActivity() {
        return activityAtThisTime;
    }
}
