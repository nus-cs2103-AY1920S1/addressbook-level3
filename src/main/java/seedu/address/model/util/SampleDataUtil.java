package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.card.Card;
import seedu.address.model.card.meaning.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;
import seedu.address.model.wordbank.WordBank;

/**
 * Contains utility methods for populating {@code WordBank} with sample data.
 */
public class SampleDataUtil {
    private static final String pokemonName = "pokemon";
    private static final String arithmeticName = "arithmetic";
    private static final String triviaName = "trivia";

    private static Card[] getPokemonCards() {
        Card card1 = new Card("abrajfbeoudnjcp", new Word("Pikachu"),
                new Meaning("Ash's first pokemon."),
                getTagSet("electric"));

        Card card2 = new Card("sdfa33234", new Word("Squirtle"),
                new Meaning("Gary's starter pokemon."),
                getTagSet("water"));

        Card card3 = new Card("charizardaiudan", new Word("Charizard"),
                new Meaning("Charmander's final evolution."),
                getTagSet("fire", "flying"));

        Card card4 = new Card("dittonfjsdodc", new Word("Gary"),
                new Meaning("Ash's rival"),
                getTagSet("asshole"));

        Card card5 = new Card("eeveeouhvdsn", new Word("Flareon"),
                new Meaning("Eevee + fire stone = ?"),
                getTagSet("fire"));

        Card card6 = new Card("eeveeouhvdsn", new Word("Mewtwo"),
                new Meaning("Mew's clone"),
                getTagSet("psychic"));
        return new Card[]{card1, card2, card3, card4, card5, card6};
    }

    private static Card[] getArithmeticCards() {
        Card card1 = new Card("threeajdshakjsd", new Word("3"),
                new Meaning("2 + 2 - 1 = ?"),
                getTagSet("Ez"));

        Card card2 = new Card("11asdfjkhalse", new Word("121"),
                new Meaning("11 * 11 = ?"),
                getTagSet("Ez"));

        Card card3 = new Card("sadjkfhaljk2", new Word("0"),
                new Meaning("1236123 modulo 9 = ?"),
                getTagSet("Medium"));

        Card card4 = new Card("1kbasdasdasdf", new Word("1024"),
                new Meaning("2 ^ 10 = ?"),
                getTagSet("Medium"));

        Card card5 = new Card("adjksfhk", new Word("29126"),
                new Meaning("square root of 848323876 = ?"),
                getTagSet("Secret"));

        Card card6 = new Card("uadiosf12", new Word("2103"),
                new Meaning("21 * 100 + 3 = ?"),
                getTagSet("Ez"));

        return new Card[]{card1, card2, card3, card4, card5, card6};
    }

    private static Card[] getTriviaCards() {
        Card card1 = new Card("adswfasf3", new Word("39"),
                new Meaning("How old is nus?"),
                getTagSet("Medium"));

        Card card2 = new Card("123124123g4", new Word("FASS"),
                new Meaning("Which faculty has the most student?"),
                getTagSet("Medium"));

        Card card3 = new Card("1234g1234g123sadrf", new Word("Dukemon"),
                new Meaning("What is the best app in the world?"),
                getTagSet("Easy"));

        Card card4 = new Card("adsrga3", new Word("SErebros"),
                new Meaning("What is our group name?"),
                getTagSet("Hard"));

        Card card5 = new Card("sdfa33", new Word("cs2103"),
                new Meaning("What is the module code of this mod? _ _ _ _ _ _"),
                getTagSet("Easy"));

        Card card6 = new Card("sadf2134", new Word("Yes"),
                new Meaning("Do you like this app?"),
                getTagSet("Easy"));

        return new Card[]{card1, card2, card3, card4, card5, card6};
    }

    public static WordBank getPokemonWordBank() {
        WordBank pokemonWb = new WordBank(pokemonName);
        for (Card sampleCard : getPokemonCards()) {
            pokemonWb.addCard(sampleCard);
        }
        return pokemonWb;
    }

    public static WordBank getArithmeticWordBank() {
        WordBank arithmeticWb = new WordBank(arithmeticName);
        for (Card arithmeticCard : getArithmeticCards()) {
            arithmeticWb.addCard(arithmeticCard);
        }
        return arithmeticWb;
    }

    public static WordBank getTriviaWordBank() {
        WordBank triviaWb = new WordBank(triviaName);
        for (Card arithmeticCard : getTriviaCards()) {
            triviaWb.addCard(arithmeticCard);
        }
        return triviaWb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }


}
