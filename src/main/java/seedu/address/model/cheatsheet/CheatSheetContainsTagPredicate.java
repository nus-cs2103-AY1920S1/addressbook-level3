package seedu.address.model.cheatsheet;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Cheatsheet}'s {@code Tag} matches any of the keywords given.
 */
public class CheatSheetContainsTagPredicate implements Predicate<CheatSheet> {
    private final Set<Tag> tags;

    public CheatSheetContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
    }

    // test on the flashcard to see if he has the tag
    @Override
    public boolean test(CheatSheet cheatSheet) {
        return tags.stream()
                .anyMatch(cheatSheet::containsTag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheatSheetContainsTagPredicate // instanceof handles nulls
                && tags.equals(((CheatSheetContainsTagPredicate) other).tags)); // state check
    }
}
