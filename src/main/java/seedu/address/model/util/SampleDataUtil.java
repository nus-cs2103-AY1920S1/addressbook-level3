package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static FlashCard[] getSampleFlashcards() {
        return new FlashCard[] {
            new FlashCard(new Question("1+1"), new Answer("2"),
                new Rating("easy"),
                getCategorySet("math")),
            new FlashCard(new Question("cell"), new Answer("smallest life unit"),
                new Rating("easy"),
                getCategorySet("Bio")),
            new FlashCard(new Question("111*111 "), new Answer("12321"),
                new Rating("easy"),
                getCategorySet("math")),
            new FlashCard(new Question("HTTP"), new Answer("HyperText transfer Protocol"),
                new Rating("good"),
                getCategorySet("cs")),
            new FlashCard(new Question("Where is NUS"), new Answer("Singapore"),
                new Rating("easy"),
                getCategorySet("general"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (FlashCard sampleFlashCard : getSampleFlashcards()) {
            sampleAb.addFlashcard(sampleFlashCard);
        }
        return sampleAb;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
