//@@author nattanyz
package dream.fcard.logic.stats;

/** Abstract class for statistics objects, like UserStats and DeckStats. */
public abstract class Stats {

    /** Starts a new Session, representing the current Session the user is engaging in. */
    public abstract void startCurrentSession();

    /** Ends the current Session the user is engaging in. */
    public abstract void endCurrentSession();

    /** Gets the current session. */
    public abstract Session getCurrentSession();
}
