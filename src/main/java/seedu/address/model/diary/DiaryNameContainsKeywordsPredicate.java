package seedu.address.model.diary;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Diary}'s {@code DiaryName} matches any of the keywords given.
 */
public class DiaryNameContainsKeywordsPredicate implements Predicate<Diary> {
    private final List<String> keywords;

    public DiaryNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Diary diary) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(diary.getDiaryName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiaryNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DiaryNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
