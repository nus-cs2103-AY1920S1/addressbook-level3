//@@author nattanyz
package dream.fcard.logic.stats;

/**
 * Represents the user's statistics.
 */
public class Stats {
    /** The one and only instance of Stats allowed to exist. */
    private static Stats userStats;

    /** List of Sessions the user has engaged in to date. */
    private static SessionList loginSessions;

    /** Current Session the user is engaging in, if the application is open. */
    private static Session currentSession;

    /** Constructs a new instance of Stats with no stored data. */
    public Stats() {
        loginSessions = new SessionList();
    }

    /** Returns the Statistics object pertaining to this user. */
    public static Stats getUserStats() {
        if (userStats == null) {
            userStats = new Stats();
        }
        return userStats;
    }

    /**
     * Creates a new Stats object from a String read from a file.
     * @param fileText String containing info about the Stats object, read from a file.
     * @return The new Stats object created.
     */
    public static Stats parseStats(String fileText) {
        // todo
        return new Stats();
    }

    /** Returns the number of login sessions. */
    public int getNumberOfLoginSessions() {
        return loginSessions.numberOfSessions();
    }

    // todo: calculate number of sessions in past week, past month etc. should this generate a list?
    // todo: possibly compare past week to previous week etc.

    /** Starts a new Session, representing the current Session the user is engaging in. */
    public static void startCurrentSession() {
        if (currentSession != null) {
            endCurrentSession(); // should not occur, but should terminate just in case
            System.out.println("Existing current session detected. Terminating it first...");
        }

        // debug (change to Logger when implemented)
        System.out.println("Starting a session...");

        currentSession = new Session(); // currentSession should be null
    }

    /** Ends the current Session the user is engaging in and saves it to the list of Sessions. */
    public static void endCurrentSession() {
        // assert current session is not null?
        try {
            currentSession.endSession();
            loginSessions.addSession(currentSession);

            // reset
            currentSession = null;

            // debug (change to Logger when implemented)
            System.out.println("Ending the current session...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Current session not found?");
        }
    }

    /** Gets the list of login sessions. */
    public static SessionList getLoginSessions() {
        return loginSessions;
    }
}
