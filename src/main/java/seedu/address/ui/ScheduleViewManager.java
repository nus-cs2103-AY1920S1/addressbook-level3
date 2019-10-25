package seedu.address.ui;

/**
 * Interface to control schedule view in the UI.
 */
public interface ScheduleViewManager {
    public ScheduleView getScheduleView();
    public void scrollNext();
    public void toggleNext();
}
