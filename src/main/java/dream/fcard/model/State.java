package dream.fcard.model;

import java.util.ArrayList;

import dream.fcard.model.exceptions.DeckNotFoundException;

/**
 * Running state of the program.
 */
public class State {

    private StateEnum currentState;
    private ArrayList<Deck> decks;

    /**
     * Constructor to create a State object with no Deck objects.
     */
    public State() {
        currentState = StateEnum.DEFAULT;
        decks = new ArrayList<>();
    }

    /**
     * Constructor to create a State object with existing Deck objects.
     *
     * @param initialDecks ArrayList of Deck objects to include in State object.
     */
    public State(ArrayList<Deck> initialDecks) {
        currentState = StateEnum.DEFAULT;
        decks = initialDecks;
    }

    /**
     * Adds new empty Deck object to decks list.
     */
    public void addDeck(String deckName) {
        decks.add(new Deck(deckName));
    }

    /**
     * Adds a deck object to decks list.
     *
     * @param deck Deck object to add into State.
     */
    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    /**
     * Removes the deck from the list of Deck objects, if there is a Deck object with a matching name.
     * Else, throw exception when no Deck object with matching name is found.
     */
    public void removeDeck(String name) throws DeckNotFoundException {
        int deckIndex = getDeckIndex(name);
        if (deckIndex == -1) {
            throw new DeckNotFoundException("Deck not found - " + name);
        }
        decks.remove(deckIndex);
    }

    /**
     * Returns the Deck object that matches in name, if a Deck with matching name exists.
     * Else, throw exception when no Deck with matching name is found.
     *
     * @param name String of name of Deck object looking for.
     * @return Deck object with name.
     * @throws DeckNotFoundException Throw exception when no matching Deck with name specified.
     */
    public Deck getDeck(String name) throws DeckNotFoundException {
        int indexOfDeck = getDeckIndex(name);
        if (indexOfDeck == -1) {
            throw new DeckNotFoundException("Deck not found - " + name);
        }
        return decks.get(indexOfDeck);
    }

    /**
     * @return
     */
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    /**
     * Replace all decks with a new set of decks. Used by `root` command.
     *
     * @param newDecks new decks
     */
    public void reloadAllDecks(ArrayList<Deck> newDecks) {
        decks = newDecks;
    }

    /**
     * Returns the index of a Deck given the Deck name, if a Deck with matching name exists.
     * Else, return -1 if no Deck with matching name is found.
     * <p>
     * Note: this method is only used internally for State processing.
     * Should not be confused with user seen indexes, since this is 0-based index.
     *
     * @param name String of name of Deck.
     * @return Integer value of index of Deck stored in list of Deck objects.
     */
    private int getDeckIndex(String name) {
        for (int i = 0; i < decks.size(); i++) {
            Deck currentDeck = decks.get(i);
            boolean isUserInputMatchDeckName = currentDeck.getName().equals(name);

            if (isUserInputMatchDeckName) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return
     */
    public StateEnum getCurrentState() {
        return currentState;
    }

    /**
     * @param deckName
     * @return
     */
    public boolean hasDeck(String deckName) {
        for (int i = 0; i < decks.size(); i++) {
            Deck currentDeck = decks.get((i));
            String currentDeckName = currentDeck.getName();
            boolean isMatchName = currentDeckName.equals(deckName);
            if (isMatchName) {
                return true;
            }
        }
        return false;
    }

    public void setCurrentState(StateEnum newState) {
        currentState = newState;
    }
}
