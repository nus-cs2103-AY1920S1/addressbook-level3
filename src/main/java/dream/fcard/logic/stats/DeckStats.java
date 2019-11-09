package dream.fcard.logic.stats;

import java.util.HashMap;

public class DeckStats extends Stats {

    /** Data structure mapping decks to their corresponding SessionLists. */
    private HashMap<String, SessionList> deckHashMap = new HashMap<>();

    /** The current Session the user is engaging in. */
    private Session currentSession;

    /** Ends the current session, and sets its score. */
    public void endCurrentSession(String score) {
        this.currentSession.setScore(score);
        endCurrentSession();
    }

    @Override
    public void startCurrentSession() {

    }

    @Override
    public void endCurrentSession() {

    }

    @Override
    public Session getCurrentSession() {
        return null;
    }

    // methods relating to changes to decks

}
