package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.wordbank.WordBank;
import seedu.address.model.card.Card;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code WordBank} with sample data.
 */
public class SampleDataUtil {
    public static Card[] getSampleCards() {
        return new Card[] {
            new Card(new Word("Abra"), new Meaning("It sleeps eighteen hours a day, but employs telekinesis even "
                    + "while sleeping."),
                    getTagSet("psychic")),
            new Card(new Word("Butterfree"), new Meaning("Its wings are covered with poisonous dust. If you see "
                    + "one flapping its wings, be careful not to inhale any of the dust."),
                    getTagSet("bug", "flying")),
            new Card(new Word("Charizard"), new Meaning("It flies around the sky in search of powerful "
                    + "opponents. It breathes fire of such great heat that it melts anything. However, it never turns "
                    + "its fiery breath on any opponent weaker than itself."),
                    getTagSet("fire", "flying")),
            new Card(new Word("Ditto"), new Meaning("Its transformation ability is perfect. However, if it "
                    + "is made to laugh, it can't maintain its disguise."),
                    getTagSet("normal")),
            new Card(new Word("Eevee"), new Meaning("Possessing an unbalanced and unstable genetic makeup, it "
                    + "conceals many possible evolutions."),
                    getTagSet("normal"))
        };
    }

    public static WordBank getSampleWordBank() {
        WordBank sampleWb = new WordBank();
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

}
