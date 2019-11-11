//@@author nattanyz
package dream.fcard.logic.stats;

import java.util.ArrayList;
import java.util.HashMap;

import dream.fcard.logic.storage.Schema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.StateHolder;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import dream.fcard.util.stats.SessionListUtil;

/**
 * Stores and handles the user's test sessions involving any deck.
 */
public class DeckStats extends Stats implements JsonInterface {

    /** Data structure mapping decks to their corresponding SessionLists. */
    private HashMap<String, SessionList> deckHashMap;

    /** The name of the current Deck the user is testing themselves on, if any. */
    private String currentDeck;

    /** Constructs a new instance of DeckStats with no stored data. */
    DeckStats() {
        // package-private; should only be called by StatsHolder
        this.deckHashMap = new HashMap<>();
        this.currentSession = null;
        this.currentDeck = null;
        logger.info("New DeckStats object created.");
    }

    /** Gets the hash map containing session lists for all decks. */
    public HashMap<String, SessionList> getDeckHashMap() {
        return this.deckHashMap;
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
        String currentDeck = this.getCurrentDeck();
        if (currentDeck == null) {
            logger.info("Current deck not found!");
            return;
        }

        if (this.currentSession != null) {
            endCurrentSession();
            logger.info("Existing test session detected. Terminating it first...");
        }

        logger.info("Starting a test session of deck: " + currentDeck + "...");

        this.currentSession = new Session();
    }

    @Override
    public void endCurrentSession() {
        String currentDeck = this.getCurrentDeck();
        if (currentDeck == null) {
            logger.info("Current deck not found!");
            return;
        }

        if (this.currentSession == null) {
            logger.info("Current test session not found!");
            return;
        }

        try {
            logger.info("Ending the current test session...");

            this.currentSession.endSession();
            this.addSessionToDeckSessionList(this.currentSession, currentDeck);

            // reset currentSession to null since this is terminated
            resetCurrentSession();

            // reset currentDeck to null since this is terminated
            resetCurrentDeck();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Current test session not found?");
        }
    }

    /** Ends the current session, and sets its score. */
    public void endCurrentSession(String score) {
        if (this.currentSession == null) {
            logger.info("Current test session not found!");
            return;
        }
        this.currentSession.setScore(score);
        endCurrentSession();
    }

    // methods relating to changes to decks

    /** Returns the SessionList related to the specified deck. */
    public SessionList getSessionListForDeck(String deckName) {
        // if the session list corresponding to the deck does not yet exist, create it
        if (!this.deckHashMap.containsKey(deckName)) {
            this.deckHashMap.put(deckName, new SessionList());
        }
        return this.deckHashMap.get(deckName);
    }

    /** Returns true if there are no decks in the DeckStats object. */
    public boolean hasNoDecks() {
        return this.deckHashMap.isEmpty();
    }

    /** Adds the given Session to the SessionList corresponding to the given deck. */
    public void addSessionToDeckSessionList(Session session, String deckName) {
        SessionList sessionList = this.getSessionListForDeck(deckName);
        sessionList.addSession(session);
        logger.info("Added a session to the sessionList for deck: " + deckName + ".");
    }

    /**
     * Creates a new SessionList corresponding to a new deck with the specified name,
     * and adds it to the deckHashMap with the supplied deckName as its key.
     * Assumes that no issues with the creating operation exist, i.e. have already been dealt with.
     * @param deckName The name of the deck to be added.
     */
    public void createNewDeck(String deckName) {
        this.deckHashMap.put(deckName, new SessionList());
    }

    /**
     * Renames a deck, i.e. removes the SessionList corresponding to the deck and re-adds it to the
     * deckHashMap with the newDeckName as its key.
     * Assumes that no issues with the renaming operation exist, i.e. have already been dealt with.
     * @param oldDeckName The original name of the deck.
     * @param newDeckName The new name of the deck.
     */
    public void renameDeck(String oldDeckName, String newDeckName) {
        SessionList sessionList = this.deckHashMap.remove(oldDeckName);
        this.deckHashMap.put(newDeckName, sessionList);
    }

    /**
     * When a deck is deleted, deletes the SessionList corresponding to the deck with the
     * supplied deckName.
     * @param deckName The name of the deck to be deleted.
     */
    public void deleteDeck(String deckName) {
        this.deckHashMap.remove(deckName);
    }

    /** Gets the total number of decks, as an int. */
    public int getTotalNumberOfDecks() {
        return this.deckHashMap.size();
    }

    /** Gets the total number of cards of all decks, as an int. */
    public int getTotalNumberOfCards() {
        State state = StateHolder.getState();
        int totalNumberOfCards = 0;
        for (String deckName : this.deckHashMap.keySet()) {
            try {
                Deck deck = state.getDeck(deckName);
                totalNumberOfCards += deck.getNumberOfCards();
            } catch (DeckNotFoundException e) {
                logger.info("Deck " + deckName + " was not found!");
            }
        }
        return totalNumberOfCards;
    }

    /** Gets a session list containing test sessions involving all decks. */
    public SessionList getTotalSessionList() {
        SessionList totalSessionList = new SessionList();
        for (SessionList sessionList : this.deckHashMap.values()) {
            totalSessionList.addSessions(sessionList);
        }
        return totalSessionList;
    }

    /** Gets the average score of all non-empty test session lists, as a String. */
    public String getAverageScore() {
        ArrayList<SessionList> sessionLists = new ArrayList<>(this.deckHashMap.values());
        return SessionListUtil.getAverageScore(sessionLists);
    }

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        JsonArray names = new JsonArray();
        JsonArray sessions = new JsonArray();
        for (String k : deckHashMap.keySet()) {
            try {
                names.add(k);
                sessions.add(getSessionListForDeck(k).toJson().getArray());
            } catch (JsonWrongValueException e) {
                System.out.println("SESSIONS MUST BE ARRAY");
            }
        }
        obj.put(Schema.STATS_DECK_STRINGS, names);
        obj.put(Schema.STATS_DECK_SESSIONS, sessions);
        return new JsonValue(obj);
    }
}
