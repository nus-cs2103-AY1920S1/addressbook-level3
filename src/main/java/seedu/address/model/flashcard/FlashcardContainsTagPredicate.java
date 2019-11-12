package seedu.address.model.flashcard;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Note}'s {@code Tag} matches any of the keywords given.
 */
public class FlashcardContainsTagPredicate implements Predicate<Flashcard> {
    private final Set<Tag> tags;

    private final Logger logger = LogsCenter.getLogger(FlashcardContainsTagPredicate.class.getName());

    public FlashcardContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
        logger.info("Filtering Flashcards that contain at least the specified tags");
    }

    // test on the flashcard to see if it has the tag
    @Override
    public boolean test(Flashcard flashcard) {
        boolean hasMatchingTags;
        if (tags.isEmpty()) {
            hasMatchingTags = false;
        } else {
            hasMatchingTags = tags.stream()
                    .allMatch(flashcard::containsTag);
        }
        return hasMatchingTags;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashcardContainsTagPredicate // instanceof handles nulls
                && tags.equals(((FlashcardContainsTagPredicate) other).tags)); // state check
    }
}
