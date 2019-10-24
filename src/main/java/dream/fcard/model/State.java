package dream.fcard.model;

import java.util.ArrayList;

import dream.fcard.model.exceptions.DeckNotFoundException;

/**
 * Running state of the program.
 */
public class State {

    private ArrayList<Deck> decks;

    /**
     * Constructor to create a State object with no Deck objects.
     */
    public State() {
        decks = new ArrayList<>();
    }

    /**
     * Constructor to create a State object with existing Deck objects.
     *
     * @param initialDecks ArrayList of Deck objects to include in State object.
     */
    public State(ArrayList<Deck> initialDecks) {
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
     * @param deck  deck object
     */
    public void addDeck(Deck deck) {
        decks.add(deck);
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
     * Replace all decks with a new set of decks. Used by `root` command.
     * @param newDecks  new decks
     */
    public void reloadAllDecks(ArrayList<Deck> newDecks) {
        decks = newDecks;
    }

    /**
     * Returns the index of a deck given the deck name, if a deck with matching name exists.
     * Else, return -1 if no deck with matching name is found.
     *
     * Note: this method is only used internally for State processing.
     * Should not be confused with user seen indexes, since this is 0-based index.
     *
     * @return index
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
}
