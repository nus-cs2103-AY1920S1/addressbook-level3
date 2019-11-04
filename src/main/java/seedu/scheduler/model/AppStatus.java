package seedu.scheduler.model;

/**
 * Encapsulates information related to the status(state) of the application.
 */
public class AppStatus {
    private static AppStatus theOnlyOne = null;
    private boolean isScheduled = false; // if the schedule algorithm has run or not

    private AppStatus() {
    }

    public static AppStatus getInstance() {
        if (theOnlyOne == null) {
            theOnlyOne = new AppStatus();
        }

        return theOnlyOne;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public boolean isScheduled() {
        return isScheduled;
    }
}
