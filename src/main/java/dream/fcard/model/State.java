package dream.fcard.model;

import java.util.ArrayList;

/**
 * State stores data representing the state of the running program
 * It should not execute logic or parsing, simply a data store object.
 */
public class State {

    public StateEnum mode = StateEnum.DEFAULT;
    public ArrayList<Deck> decks;
    public Deck createModeDeck = null;

    public State() {
        this(new ArrayList<>());
    }

    public State(ArrayList<Deck> initialDecks) {
        decks = initialDecks;
    }

    public State(State oldState) {
        decks = oldState.decks;
    }
}
