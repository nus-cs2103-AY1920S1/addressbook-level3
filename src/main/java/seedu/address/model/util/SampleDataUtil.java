package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Rating;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Question;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static FlashCard[] getSamplePersons() {
        return new FlashCard[] {
            new FlashCard(new Question("Alex Yeoh"), new Answer("87438807"),
                new Rating("good"),
                getTagSet("friends")),
            new FlashCard(new Question("Bernice Yu"), new Answer("99272758"),
                new Rating("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new FlashCard(new Question("Charlotte Oliveiro"), new Answer("93210283"),
                new Rating("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new FlashCard(new Question("David Li"), new Answer("91031282"),
                new Rating("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new FlashCard(new Question("Irfan Ibrahim"), new Answer("92492021"),
                new Rating("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new FlashCard(new Question("Roy Balakrishnan"), new Answer("92624417"),
                new Rating("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (FlashCard sampleFlashCard : getSamplePersons()) {
            sampleAb.addFlashcard(sampleFlashCard);
        }
        return sampleAb;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
