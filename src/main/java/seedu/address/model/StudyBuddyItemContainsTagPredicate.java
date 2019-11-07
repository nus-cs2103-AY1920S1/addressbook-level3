package seedu.address.model;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code StudyBuddyItem}'s {@code Tag} matches any of the tags given.
 */
public class StudyBuddyItemContainsTagPredicate implements Predicate<StudyBuddyItem> {
    private final Set<Tag> tags;

    private final Logger logger = LogsCenter.getLogger(StudyBuddyItemContainsTagPredicate.class.getName());

    public StudyBuddyItemContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
        logger.info("Filtering StudyBuddyItems that contain at least the specified tags");
    }

    @Override
    public boolean test(StudyBuddyItem studyBuddyItem) {
        boolean hasMatchingTags;
        if (tags.isEmpty()) {
            hasMatchingTags = false;
        } else {
            hasMatchingTags = tags.stream()
                    .allMatch(studyBuddyItem::containsTag);
        }
        return hasMatchingTags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudyBuddyItemContainsTagPredicate // instanceof handles nulls
                && tags.equals(((StudyBuddyItemContainsTagPredicate) other).tags)); // state check
    }

}
