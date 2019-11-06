package seedu.scheduler.ui;

/**
 * API of refresh listener.
 */
public interface RefreshListener {

    /**
     * Refresh the Ui when data is imported from .csv file.
     */
    void scheduleDataUpdated();

    /**
     * Refresh the Ui when interviewee data is updated
     */
    void intervieweeListUpdated();

    /**
     * Refresh the Ui when interviewer data is updated
     */
    void interviewerListUpdated();
}
