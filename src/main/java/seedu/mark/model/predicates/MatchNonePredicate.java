package seedu.mark.model.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;

/**
 * Tests that a {@code Bookmark} does not match any of the {@code Predicate}s
 * that were specified when creating this {@code MatchNonePredicate}.
 */
public class MatchNonePredicate implements Predicate<Bookmark> {
    private final List<Predicate<Bookmark>> predicates;

    public MatchNonePredicate(List<Predicate<Bookmark>> predicates) {
        this.predicates = predicates; // predicates to match
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return predicates.stream()
                .noneMatch(predicate -> predicate.test(bookmark));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchNonePredicate // instanceof handles nulls
                && predicates.equals(((MatchNonePredicate) other).predicates)); // state check
    }

}
