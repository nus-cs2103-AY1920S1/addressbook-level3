package seedu.flashcard.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.Word;
import seedu.flashcard.model.tag.Tag;

/**
 * Generate the sample data to make the default flashcard list nicer.
 */
public class SampleDataUtil {

    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[]{
            new Flashcard(new Word("Compactness"), getChoiceSet(),
                new Definition("For any open covering of a metric space, there is a finite subcover."),
                     getTagSet("mathematics")),
            new Flashcard(new Word("Completeness"), getChoiceSet(),
                new Definition("Any Cauchy sequence converges in this metric space"),
                getTagSet("mathematics")),
            new Flashcard(new Word("Mount Blanc Tunnel"),
                    getChoiceSet("An 11611 meters tunnel on the boarder of France and Italy.",
                            "An 9000 meters tunnel on the boarder of France and Italy.",
                            "An 300 meters tunnel on the boarder of France and Italy."),
                new Definition("a"),
                getTagSet("geography")),
            new Flashcard(new Word("Kenetsu Tunnel"), getChoiceSet(),
                new Definition("An 10933 meters tunnel in Japan"),
                getTagSet("geography")),
        };
    }

    public static ReadOnlyFlashcardList getSampleFlashcardList() {
        FlashcardList samplefl = new FlashcardList();
        for (Flashcard flashcard : getSampleFlashcards()) {
            samplefl.addFlashcard(flashcard);
        }
        return samplefl;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Choice> getChoiceSet(String... strings) {
        return Arrays.stream(strings)
                .map(Choice::new)
                .collect(Collectors.toSet());
    }

}
