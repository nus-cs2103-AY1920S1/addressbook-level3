package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.CardBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.CardNumber;
import seedu.address.model.card.Cvc;
import seedu.address.model.card.Description;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataCardUtil {
    public static Card[] getSampleCards() {
        return new Card[] {
            new Card(new Description("POSB Debit"), new CardNumber("2048293058291847"), new Cvc("023"),
                    getTagSet("groceries")),
            new Card(new Description("POSB Credit"), new CardNumber("2938710593829573"), new Cvc("521"),
                    getTagSet("petrol")),
            new Card(new Description("OCBC Debit"), new CardNumber("2740284759384729"), new Cvc("633"),
                    getTagSet("water bills")),
            new Card(new Description("Maybank Credit"), new CardNumber("2948375928375918"), new Cvc("192"),
                    getTagSet("electricity bills")),
            new Card(new Description("HSBC Debit"), new CardNumber("2958285730295837"), new Cvc("923"),
                    getTagSet("rent")),
            new Card(new Description("HSBC Credit"), new CardNumber("2037281958374028"), new Cvc("294"),
                    getTagSet("rent")),
        };
    }

    public static CardBook getSampleCardBook() {
        CardBook sampleCb = new CardBook();
        for (Card sampleCard : getSampleCards()) {
            sampleCb.addCard(sampleCard);
        }
        return sampleCb;
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
