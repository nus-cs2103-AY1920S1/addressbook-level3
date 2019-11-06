//@@author nattanyz
package dream.fcard.logic.stats;

/** Abstract class for statistics objects, like UserStats and DeckStats. */
public abstract class Stats {

    /** List of Sessions the user has engaged in. */
    protected SessionList sessionList;

    /** The current Session the user is engaging in. */
    protected Session currentSession;

    public Stats() {
        this.sessionList = new SessionList();
        this.currentSession = null;
    }

    /** Sets the sessionList of the current Stats object to the given newSessionList. */
    public void setSessionList(SessionList newSessionList) {
        this.sessionList = newSessionList;
    }

    /** Gets the list of sessions. */
    public SessionList getSessionList() {
        return this.sessionList;
    }

    /** Starts a new Session, representing the current Session the user is engaging in. */
    public void startCurrentSession() {
        if (this.currentSession != null) {
            endCurrentSession(); // should not occur, but should terminate just in case
            System.out.println("Existing current session detected. Terminating it first...");
        }

        // debug (change to Logger when implemented)
        System.out.println("Starting a session...");

        this.currentSession = new Session(); // currentSession should be null
    }

    /** Ends the current Session the user is engaging in and saves it to the list of Sessions. */
    public void endCurrentSession() {
        // assert current session is not null?
        try {
            this.currentSession.endSession();
            this.sessionList.addSession(currentSession);

            // reset currentSession to null since this is terminated
            this.currentSession = null;

            // debug (change to Logger when implemented)
            System.out.println("Ending the current session...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Current session not found?");
        }
    }

    /** Gets the current session. */
    public Session getCurrentSession() {
        return this.currentSession;
    }
}
