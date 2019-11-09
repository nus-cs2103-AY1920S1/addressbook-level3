package dream.fcard.logic.stats;

import java.util.HashMap;

public class DeckStats extends Stats {

    /** Data structure mapping decks to their corresponding SessionLists. */
    private HashMap<String, SessionList> deckHashMap = new HashMap<>();

    /** The current Session the user is engaging in. */
    private Session currentSession;

    /** The name of the current Deck the user is testing themselves on, if any. */
    private String currentDeck;

    /** Constructs a new instance of DeckStats with no stored data. */
    public DeckStats() {
        this.deckHashMap = new HashMap<>();
        this.currentSession = null;
        this.currentDeck = null;
        logger.info("New DeckStats object created.");
    }

    /** Ends the current session, and sets its score. */
    public void endCurrentSession(String score) {
        this.currentSession.setScore(score);
        endCurrentSession();
    }

    /** Sets the name of the deck under test. */
    public void setCurrentDeck(String deckName) {
        this.currentDeck = deckName;
    }

    /** Gets the name of the deck under test, if any. */
    public String getCurrentDeck() {
        return this.currentDeck;
    }

    /** Resets the name of the deck under test, when no test is being conducted. */
    public void resetCurrentDeck() {
        this.currentDeck = null;
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
