package com.dukeacademy.model.question;

import java.util.List;
import java.util.function.Predicate;

import com.dukeacademy.commons.util.StringUtil;

/**
 * Tests that a {@code Question}'s {@code Title} matches any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<Question> {
    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Question question) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(question.getTitle().fullTitle, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleContainsKeywordsPredicate
                // instanceof handles nulls
                && keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
