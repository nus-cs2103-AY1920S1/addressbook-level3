package seedu.address.model.day;

public class HalfHour {
    private ActivityStub activityAtThisTime;
    private boolean isOccupied;

    public HalfHour() {
        isOccupied = false;
    }

    public HalfHour(ActivityStub activity) {
        this.activityAtThisTime = activity;
        this.isOccupied = true;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public ActivityStub getActivity() {
        return activityAtThisTime;
    }
}
