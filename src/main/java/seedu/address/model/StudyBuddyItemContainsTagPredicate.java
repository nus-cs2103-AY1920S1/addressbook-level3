package seedu.address.model;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code StudyBuddyItem}'s {@code Tag} matches any of the tags given.
 */
public class StudyBuddyItemContainsTagPredicate implements Predicate<StudyBuddyItem> {
    private final Set<Tag> tags;

    public StudyBuddyItemContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
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
