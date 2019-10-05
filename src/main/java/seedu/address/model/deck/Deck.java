package seedu.address.model.deck;

import seedu.address.model.flashcard.Flashcard;
import java.util.List;

/**
 * Stub class for immutable Deck of Flashcards
 */
public class Deck {

    private List<Flashcard> listOfFlashcards;

    protected Deck(List<Flashcard> listOfFlashcards) {
        this.listOfFlashcards = listOfFlashcards;
    }

    public Flashcard get(int index) {
        return listOfFlashcards.get(index);
    }

    public int size() {
        return listOfFlashcards.size();
    }
}
