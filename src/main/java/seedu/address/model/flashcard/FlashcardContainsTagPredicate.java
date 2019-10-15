package seedu.address.model.flashcard;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

public class FlashcardContainsTagPredicate implements Predicate<Flashcard> {
    private final Set<Tag> tags;

    public FlashcardContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
    }

    // test on the flashcard to see if he has the tag
    @Override
    public boolean test(Flashcard flashcard) {
        return tags.stream()
                .anyMatch(flashcard::containsTag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashcardContainsTagPredicate // instanceof handles nulls
                && tags.equals(((FlashcardContainsTagPredicate) other).tags)); // state check
    }
}
