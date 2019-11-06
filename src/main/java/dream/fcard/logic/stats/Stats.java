//@@author nattanyz
package dream.fcard.logic.stats;

import java.util.ArrayList;

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

}
