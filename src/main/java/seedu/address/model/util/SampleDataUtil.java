package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;
import seedu.address.model.wordbank.WordBank;

/**
 * Contains utility methods for populating {@code WordBank} with sample data.
 */
public class SampleDataUtil {
    private static final String sampleName = "sample";
    private static final String arithmeticName = "arithmetic";

    private static Card[] getSampleCards() {
        Card card1 = new Card("abrajfbeoudnjcp", new Word("Abra"),
                new Meaning("It sleeps eighteen hours a day, but employs telekinesis even while sleeping."),
                getTagSet("psychic"));

        Card card2 = new Card("butterfreejdfbo", new Word("Butterfree"),
                new Meaning("Its wings are covered with poisonous dust. If you see "
                        + "one flapping its wings, be careful not to inhale any of the dust."),
                getTagSet("bug", "flying"));

        Card card3 = new Card("charizardaiudan", new Word("Charizard"),
                new Meaning("It flies around the sky in search of powerful opponents. It breathes fire of "
                        + "such great heat that it melts anything. However, it never turns "
                        + "its fiery breath on any opponent weaker than itself."),
                getTagSet("fire", "flying"));

        Card card4 = new Card("dittonfjsdodc", new Word("Ditto"),
                new Meaning("Its transformation ability is perfect. However, if it "
                        + "is made to laugh, it can't maintain its disguise."),
                getTagSet("normal"));

        Card card5 = new Card("eeveeouhvdsn", new Word("Eevee"),
                new Meaning("Possessing an unbalanced and unstable genetic makeup, it "
                        + "conceals many possible evolutions."),
                getTagSet("normal"));
        return new Card[]{card1, card2, card3, card4, card5};
    }

    private static Card[] getArithmeticCards() {
        Card card1 = new Card("threeajdshakjsd", new Word("3"),
                new Meaning("2 + 2 - 1 = ?"),
                getTagSet("No brainer"));

        Card card2 = new Card("11asdfjkhalse", new Word("121"),
                new Meaning("11 * 11 = ?"),
                getTagSet("Elementary math"));

        Card card3 = new Card("sadjkfhaljk2", new Word("0"),
                new Meaning("1236123 modulo 9 = ?"),
                getTagSet("Modulo"));

        Card card4 = new Card("1kbasdasdasdf", new Word("1024"),
                new Meaning("2 ^ 10 = ?"),
                getTagSet("1 Byte"));

        Card card5 = new Card("adjksfhk", new Word("29126"),
                new Meaning("square root of 848323876 = ?"),
                getTagSet("Secret code"));
        return new Card[]{card1, card2, card3, card4, card5};
    }

    public static WordBank getSampleWordBank() {
        WordBank sampleWb = new WordBank(sampleName);
        for (Card sampleCard : getSampleCards()) {
            sampleWb.addCard(sampleCard);
        }
        return sampleWb;
    }

    public static WordBank getArithmeticWordBank() {
        WordBank arithmeticWb = new WordBank(arithmeticName);
        for (Card arithmeticCard : getArithmeticCards()) {
            arithmeticWb.addCard(arithmeticCard);
        }
        return arithmeticWb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static String getSampleName() {
        return sampleName;
    }
}
