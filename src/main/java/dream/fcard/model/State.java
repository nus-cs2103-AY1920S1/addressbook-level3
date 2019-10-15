package dream.fcard.model;

import java.util.ArrayList;

import dream.fcard.model.exceptions.DeckNotFoundException;

/**
 * Running state of the program
 */
public class State {

    private ArrayList<Deck> decks;

    public State() {
        decks = new ArrayList<>();
    }

    public State(ArrayList<Deck> initialDecks) {
        decks = initialDecks;
    }

    /**
     * Adds new empty deck object to decks list.
     */
    public void addDeck(String deckName) {
        decks.add(new Deck(deckName));
    }

    /**
     * Removes the deck from the decks list, if there is a deck with a matching name.
     * Else, throw exception when no deck with matching name is found.
     */
    public void removeDeck(String deckname) throws DeckNotFoundException {
        int deckIndex = getDeckIndex(deckname);
        if (deckIndex == -1) {
            throw new DeckNotFoundException(new Exception());
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
            throw new DeckNotFoundException(new Exception());
        }
        return decks.get(indexOfDeck);
    }

    /**
     * Returns the index of a deck given the deck name, if a deck with matching name exists.
     * Else, return -1 if no deck with matching name is found.
     *
     * @return index
     */
    private int getDeckIndex(String deckName) {
        for (int i = 0; i < decks.size(); i++) {
            Deck currentDeck = decks.get(i);
            boolean isUserInputMatchDeckName = currentDeck.getDeckName().equals(deckName);

            if (isUserInputMatchDeckName) {
                return i;
            }
        }
        return -1;
    }
}
