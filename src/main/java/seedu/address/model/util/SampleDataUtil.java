package seedu.address.model.util;


import seedu.address.model.ReadOnlyWordBank;
import seedu.address.model.WordBank;
import seedu.address.model.card.Card;
import seedu.address.model.card.Description;
import seedu.address.model.card.Name;
import seedu.address.model.tag.Tag;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains utility methods for populating {@code WordBank} with sample data.
 */
public class SampleDataUtil {
    public static Card[] getSampleCards() {
        return new Card[] {
            new Card(new Name("Pikachu"), new Description("PIKAPIKAAAAA"),
                    getTagSet("friends")),
            new Card(new Name("Eevee"), new Description("Woah! So many possible evolutions."),
                    getTagSet("friends")),
            new Card(new Name("Ditto"), new Description("I can transform and breed with anyone."),
                    getTagSet("friends")),
            new Card(new Name("Magikarp"), new Description("Just wait.. I'm going to evolve to something STRONG!"),
                    getTagSet("friends")),
            new Card(new Name("Snorlax"), new Description("ZZzzZZzzzzZZzzz"),
                    getTagSet("friends")),
            new Card(new Name("Psyduck"), new Description("PSY DUCK!"), getTagSet("friends"))
        };
    }

    public static ReadOnlyWordBank getSampleWordBank() {
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
