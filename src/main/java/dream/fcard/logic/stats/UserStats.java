//@@author nattanyz
package dream.fcard.logic.stats;

/**
 * Stores and handles the user's login sessions.
 */
public class UserStats extends Stats {

    /** List of Sessions the user has engaged in. */
    private SessionList sessionList;

    /** Constructs a new instance of UserStats with no stored data. */
    UserStats() {
        // package-private; should only be called by StatsHolder
        this.sessionList = new SessionList();
        this.currentSession = null;
        logger.info("New UserStats object created.");
    }

    /** Sets the sessionList of the current Stats object to the given newSessionList. */
    public void setSessionList(SessionList newSessionList) {
        this.sessionList = newSessionList;
    }

    /** Gets the list of sessions. */
    public SessionList getSessionList() {
        return this.sessionList;
    }

    @Override
    public void startCurrentSession() {
        if (this.currentSession != null) {
            endCurrentSession(); // should not occur, but should terminate just in case
            logger.info("Existing login session detected. Terminating it first...");
        }

        logger.info("Starting a login session...");

        this.currentSession = new Session();
    }

    @Override
    public void endCurrentSession() {
        if (this.currentSession == null) {
            logger.info("Current login session not found!");
            return;
        }

        try {
            this.currentSession.endSession();
            this.sessionList.addSession(currentSession);

            // reset currentSession to null since this is terminated
            resetCurrentSession();

            logger.info("Ending the current login session...");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Current login session not found?");
        }
    }

    @Override
    public Session getCurrentSession() {
        return this.currentSession;
    }
}
