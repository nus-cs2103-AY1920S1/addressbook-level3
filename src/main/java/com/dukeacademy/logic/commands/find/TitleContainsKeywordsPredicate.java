package com.dukeacademy.logic.commands.find;

import java.util.List;
import java.util.function.Predicate;

import com.dukeacademy.commons.util.StringUtil;
import com.dukeacademy.model.question.Question;

/**
 * Tests that a {@code Question}'s {@code Title} matches any of the keywords
 * given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<Question> {
    private final List<String> keywords;

    /**
     * Instantiates a new Title contains keywords predicate.
     *
     * @param keywords the keywords
     */
    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }
    @Override
    public boolean test(Question question) {
        return keywords.stream()
                       .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(question.getTitle(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }
}
