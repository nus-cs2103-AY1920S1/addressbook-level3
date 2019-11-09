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
        // assert that currentDeck is not null?

        // replace with assert?
        if (this.currentSession != null) {
            endCurrentSession(); // should not occur, but should terminate just in case
            logger.info("Existing test session detected. Terminating it first...");
        }

        // debug (change to Logger when implemented)
        logger.info("Starting a test session...");

        this.currentSession = new Session();
    }

    @Override
    public void endCurrentSession() {
        // assert that currentDeck is not null?
        String currentDeck = this.getCurrentDeck();

        if (this.currentSession == null) {
            logger.info("Current login session not found!");
            return;
        }

        try {
            this.currentSession.endSession();
            //this.sessionList.addSession(currentSession);

            // reset currentSession to null since this is terminated
            this.currentSession = null;

            logger.info("Ending the current login session...");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Current login session not found?");
        }
    }

    @Override
    public Session getCurrentSession() {
        return null;
    }

    // methods relating to changes to decks

    /** Adds the given Session to the SessionList corresponding to the given deck. */
    public void addSessionToDeckSessionList(Session session, String deckName) {
        // if deck not found in hashmap, throw deck not found exception?

    }

    /**
     * Renames a deck.
     * Assumes that no issues with the renaming operation exist, i.e. have already been dealt with.
     * @param oldDeckName The original name of the deck.
     * @param newDeckName The new name of the deck.
     */
    public void renameDeck(String oldDeckName, String newDeckName) {

    }

    /**
     * Deletes a deck.
     * @param deckName The name of the deck to be deleted.
     */
    public void deleteDeck(String deckName) {

    }
}
