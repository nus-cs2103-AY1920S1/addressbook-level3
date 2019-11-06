package seedu.scheduler.ui;

/**
 * API of TabListener.
 */
public interface TabListener {

    /**
     * Signal main window to change to schedule tab.
     */
    void changeTabSchedule();

    /**
     * Signal main window to change to interviewee tab.
     */
    void changeTabInterviewee();

    /**
     * Signal main window to change to interviewer tab.
     */
    void changeTabInterviewer();
}
