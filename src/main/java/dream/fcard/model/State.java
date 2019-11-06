package dream.fcard.model;

import java.util.ArrayList;

import dream.fcard.logic.stats.UserStats;
import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.exceptions.DeckNotFoundException;

/**
 * State stores data representing the state of the running program
 * It should not execute logic or parsing, simply a data store object.
 */
public class State {
    private ArrayList<Deck> decks;
    private StateEnum currState;
    private Deck currentDeck;
    private UserStats userStats;

    /**
     * Constructor to create a State object with no Deck objects.
     */
    public State() {
        decks = StorageManager.loadDecks();
        currState = StateEnum.DEFAULT;
        userStats = new UserStats();
    }

    /**
     * Return the current deck in Create mode.
     * @return the deck in Create Mode.
     */
    public Deck getCurrentDeck() {
        return currentDeck;
    }


    /**
     * Returns false if decks is non-empty, true if decks is empty.
     */
    public boolean isEmpty() {
        return decks.size() == 0;
    }

    /**
     * Returns the list of decks.
     */
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    /** Gets the UserStats object. */
    public UserStats getUserStats() {
        return userStats;
    }

    /** Sets the UserStats object. */
    public void setUserStats(UserStats stats) {
        userStats = stats;
    }

    /**
     * Adds a new empty Deck object to decks list.
     */
    public void addDeck(String deckName) {
        Deck temp = new Deck(deckName);
        decks.add(temp);
        this.currentDeck = temp;
    }

    /**
     * Adds a deck object to decks list.
     *
     * @param deck deck object
     */
    public void addDeck(Deck deck) {
        decks.add(deck);
        this.currentDeck = deck;
    }


    /**
     * Removes the deck from the decks list, if there is a deck with a matching name.
     * Else, throw exception when no deck with matching name is found.
     */
    public void removeDeck(String name) throws DeckNotFoundException {
        int deckIndex = getDeckIndex(name);
        if (deckIndex == -1) {
            throw new DeckNotFoundException("Deck not found - " + name);
        }
        decks.remove(deckIndex);
    }

    /**
     * Returns the deck object that matches in name, if a deck with matching name exists.
     * Else, throw exception when no deck with matching name is found.
     *
     * @return index
     */
    public Deck getDeck(String name) throws DeckNotFoundException {
        int indexOfDeck = getDeckIndex(name);
        if (indexOfDeck == -1) {
            throw new DeckNotFoundException("Deck not found - " + name);
        }
        return decks.get(indexOfDeck);
    }


    /**
     * Returns the index of a deck given the deck name, if a deck with matching name exists.
     * Else, return -1 if no deck with matching name is found.
     * <p>
     * Note: this method is only used internally for State processing.
     * Should not be confused with user seen indexes, since this is 0-based index.
     *
     * @return index
     */
    private int getDeckIndex(String name) {
        for (int i = 0; i < decks.size(); i++) {
            Deck currentDeck = decks.get(i);
            boolean isUserInputMatchDeckName = currentDeck.getDeckName().equals(name);

            if (isUserInputMatchDeckName) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Set current state of the app.
     * @param currState the current state.
     */
    public void setCurrState(StateEnum currState) {
        this.currState = currState;
        LogsCenter.getLogger(State.class).info("Entering state: + this.currState");
    }

    /**
     * Get current state of the app.
     * @return the current state.
     */
    public StateEnum getCurrState() {
        return currState;
    }

    /**
     * Checks whether a deck with the given name exists. To prevent duplicates.
     * @param name
     * @return the index.
     */
    public int hasDeckName(String name) {
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getName() == name) {
                return i;
            }
        }
        return -1;
    }
}
