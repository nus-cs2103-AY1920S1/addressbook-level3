package seedu.address.model.cheatsheet;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Cheatsheet}'s {@code Tag} matches any of the keywords given.
 */
public class CheatSheetContainsTagPredicate implements Predicate<CheatSheet> {
    private final Set<Tag> tags;

    private final Logger logger = LogsCenter.getLogger(CheatSheetContainsTagPredicate.class.getName());

    public CheatSheetContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
        logger.info("Filtering CheatSheets that contain at least the specified tags");
    }

    // test on the flashcard to see if he has the tag
    @Override
    public boolean test(CheatSheet cheatSheet) {
        boolean hasMatchingTags;
        if (tags.isEmpty()) {
            hasMatchingTags = false;
        } else {
            hasMatchingTags = tags.stream()
                    .allMatch(cheatSheet::containsTag);
        }
        return hasMatchingTags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheatSheetContainsTagPredicate // instanceof handles nulls
                && tags.equals(((CheatSheetContainsTagPredicate) other).tags)); // state check
    }
}
