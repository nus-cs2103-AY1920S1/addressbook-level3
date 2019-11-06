//@@author nattanyz
package dream.fcard.logic.stats;

/**
 * Represents the user's statistics. Contains a SessionList containing all the user's login sessions.
 */
public class UserStats extends Stats {

    /** List of Sessions the user has engaged in. */
    private SessionList sessionList;

    /** The current Session the user is engaging in. */
    private Session currentSession;

    /** Constructs a new instance of UserStats with no stored data. */
    public UserStats() {
        super();
        System.out.println("New UserStats object created");
    }

    // todo: calculate number of sessions in past week, past month etc. should this generate a list?
    // todo: possibly compare past week to previous week etc.

    // methods copied from Stats
}
