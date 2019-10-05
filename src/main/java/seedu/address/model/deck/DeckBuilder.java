package seedu.address.model.deck;

import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Hint;
import seedu.address.model.flashcard.Question;

import java.util.Arrays;

public class DeckBuilder {

    public static final Flashcard singaporeCard = new Flashcard(
            new Answer("Singapore"),
            new Question("The Lion City?"),
            new Hint("Dummy Hint"));

    public static final Flashcard malaysiaCard = new Flashcard(
            new Answer("Malaysia"),
            new Question("Singapore's Neighbour?"),
            new Hint("Dummy Hint"));

    public static final Flashcard japanCard = new Flashcard(
            new Answer("Japan"),
            new Question("Sushi Land?"),
            new Hint("Dummy Hint"));

    public static final Flashcard damithCard = new Flashcard(
            new Answer("Damith"),
            new Question("CS2103T Prof?"),
            new Hint("Damith Hint"));

    public static Deck makeNewDeck(Flashcard... cards) {
        return new Deck(Arrays.asList(cards));
    }
}
