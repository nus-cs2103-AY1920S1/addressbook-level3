package dream.fcard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.exceptions.DeckNotFoundException;

/**
 * Running state of the program.
 */
public class State {
    private static State state;
    private ArrayList<Deck> decks;
    private boolean isEditMode;
    private boolean isCreateMode;
    private HashMap<String, Consumer> consumerHashMap;


    /**
     * Constructor to create a State object with existing Deck objects.
     *
     * @param initialDecks ArrayList of Deck objects to include in State object.
     */
    public State(ArrayList<Deck> initialDecks) {
        decks = initialDecks;
    }

    /**
     * Constructor to create a State object with no Deck objects.
     */
    public State() {
        decks = StorageManager.loadDecks();
        consumerHashMap = new HashMap<>();
    }

    /**
     * State is a singleton to avoid passing the state object through too many layers of objects.
     *
     * @return the singleton state object
     */
    public static State getState() {
        if (state == null) {
            state = new State();
        }
        return state;
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


    /**
     * Adds a new empty Deck object to decks list.
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
     * Load decks from StorageManager.
     *
     * @param newDecks the array list of all decks in Storage.
     */
    public void reloadAllDecks(ArrayList<Deck> newDecks) {
        decks = newDecks;
    }

    /**
     * Returns the index of a deck given the deck name, if a deck with matching name exists.
     * Else, return -1 if no deck with matching name is found.
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

    public void addConsumer(String identifier, Consumer c) {
        consumerHashMap.putIfAbsent(identifier, c);
    }

    /**
     * This method of getting consumers generifies the type of input which leads to compiler warnings.
     * As such, the suppress warning annotations used whenever this method is called
     * are due to the unchecked generic Consumer types.
     *
     * @param identifier name of the Consumer as recorded in ConsumerSchema
     * @return the Consumer
     */
    public Consumer getConsumer(String identifier) {
        return consumerHashMap.get(identifier);
    }
}
