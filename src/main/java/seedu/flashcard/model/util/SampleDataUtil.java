package seedu.flashcard.model.util;


import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.Word;
import seedu.flashcard.model.tag.Tag;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SampleDataUtil {

    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[]{
                new Flashcard(new Word("Compactness"),
                        new Definition("For any open covering of a metric space, there is a finite subcover."),
                        getTagSet("mathematics")),
                new Flashcard(new Word("Completeness"),
                        new Definition("Any Cauchy sequence converges in this metric space"),
                        getTagSet("mathematics")),
                new Flashcard(new Word("Mount Blanc Tunnel"),
                        new Definition("An 11611 meters tunnel on the boarder of France and Italy."),
                        getTagSet("geography")),
                new Flashcard(new Word("Kenetsu Tunnel"),
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
}
