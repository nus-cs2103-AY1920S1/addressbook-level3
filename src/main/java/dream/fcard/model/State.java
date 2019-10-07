package dream.fcard.model;

import java.util.ArrayList;

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
}
