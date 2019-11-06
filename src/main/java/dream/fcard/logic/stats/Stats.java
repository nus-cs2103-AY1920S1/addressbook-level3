//@@author nattanyz
package dream.fcard.logic.stats;

import java.time.Duration;
import java.util.ArrayList;

import dream.fcard.util.stats.DateTimeUtil;

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

    /** Returns the number of sessions in the sessionList contained inside this Stats object. */
    public int getNumberOfSessions() {
        return this.sessionList.getNumberOfSessions();
    }

    /** Adds the given Session to the sessionList contained inside this Stats object. */
    public void addSession(Session session) {
        this.sessionList.addSession(session);
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

    /** Gets the list of sessions, as the underlying ArrayList, to display in the GUI. */
    public ArrayList<Session> getSessionsAsArrayList() {
        return this.sessionList.getSessionArrayList();
    }

    /** Gets the current session. */
    public Session getCurrentSession() {
        return this.currentSession;
    }

    /** Gets the total length of time spent in sessions, as a String. */
    public String getTotalDurationOfSessionsAsString() {
        return this.sessionList.getTotalDurationAsString();
    }

    /** Gets the average length of time spent in sessions, as a String. */
    public String getAverageDurationOfSessionsAsString() {
        Duration averageDuration = DateTimeUtil.getAverageDuration(this.sessionList);
        return DateTimeUtil.getStringFromDuration(averageDuration);
    }
}
