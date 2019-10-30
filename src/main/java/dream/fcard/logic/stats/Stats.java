package dream.fcard.logic.stats;

/**
 * Represents the user's statistics.
 */
public class Stats {

    private SessionList loginSessions;

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
    public int numberOfLoginSessions() {
        return this.loginSessions.numberOfSessions();
    }

    // todo: calculate number of sessions in past week, past month etc. should this generate a list?
    // todo: possibly compare past week to previous week etc.
}
