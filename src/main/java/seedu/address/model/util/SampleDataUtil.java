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
    private static final String name = "sample";

    public static Card[] getSampleCards() {
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

    public static WordBank getSampleWordBank() {
        WordBank sampleWb = new WordBank(name);
        for (Card sampleCard : getSampleCards()) {
            sampleWb.addCard(sampleCard);
        }
        return sampleWb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static String getName() {
        return name;
    }
}
