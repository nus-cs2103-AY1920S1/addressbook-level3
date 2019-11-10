package seedu.address.model.lesson;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Lesson}'s keywords matches any of the keywords given.
 */
public class LessonContainsKeywordsPredicate implements Predicate<Lesson> {
    private final List<String> keywords;

    public LessonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Lesson lesson) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(lesson.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((LessonContainsKeywordsPredicate) other).keywords)); // state check
    }
}
