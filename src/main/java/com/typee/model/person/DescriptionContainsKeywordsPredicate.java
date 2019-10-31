package com.typee.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.typee.model.engagement.Engagement;

/**
 * Tests that an {@code Engagement}'s description matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Engagement> {
    private final List<String> keywords;

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Engagement engagement) {
        for (String keyword:keywords) {
            if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE)
                    .matcher(engagement.getDescription()).find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
