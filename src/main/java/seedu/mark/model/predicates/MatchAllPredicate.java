package seedu.mark.model.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;

/**
 * Tests that a {@code Bookmark} matches all of the {@code Predicate}s that
 * were specified when creating this {@code MatchAllPredicate}.
 *
 * <p>{@code MatchAllPredicate#test(Bookmark)} should return true if and only if
 * this {@code MatchAllPredicate} contains predicates to test and none of them
 * return false when tested on the {@code bookmark}.
 */
public class MatchAllPredicate implements Predicate<Bookmark> {
    private final List<Predicate<Bookmark>> predicates;

    public MatchAllPredicate(List<Predicate<Bookmark>> predicates) {
        this.predicates = predicates; // predicates to match
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return !predicates.isEmpty()
                && predicates.stream().allMatch(predicate -> predicate.test(bookmark));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchAllPredicate // instanceof handles nulls
                && predicates.equals(((MatchAllPredicate) other).predicates)); // state check
    }

}
