package seedu.eatme.model.eatery;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.eatme.commons.util.StringUtil;

/**
 * Tests that a {@code Eatery}'s attributes ({@code Name}, {@code Address}, {@code Category}, {@code Tags})
 * matches any of the keywords given.
 */
public class EateryAttributesContainsKeywordsPredicate implements Predicate<Eatery> {
    private final List<String> nameKeywords;
    private final List<String> addressKeywords;
    private final List<String> categoryKeywords;
    private final List<String> tagKeywords;

    public EateryAttributesContainsKeywordsPredicate(List<String> nameKeywords, List<String> addressKeywords,
                                                     List<String> categoryKeywords, List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.addressKeywords = addressKeywords;
        this.categoryKeywords = categoryKeywords;
        this.tagKeywords = tagKeywords;
    }

    public EateryAttributesContainsKeywordsPredicate(List<String> nameKeywords) {
        this.nameKeywords = nameKeywords;
        this.addressKeywords = new ArrayList<>();
        this.categoryKeywords = new ArrayList<>();
        this.tagKeywords = new ArrayList<>();
    }

    @Override
    public boolean test(Eatery eatery) {
        boolean nameMatch = !nameKeywords.isEmpty() && nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(eatery.getName().fullName, keyword));
        boolean addressMatch = !addressKeywords.isEmpty() && addressKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(eatery.getAddress().value, keyword));
        boolean categoryMatch = !categoryKeywords.isEmpty() && categoryKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(eatery.getCategory().getName(), keyword));
        boolean tagMatch = !tagKeywords.isEmpty() && tagKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        eatery.getTags().stream().map(Tag::getName).collect(Collectors.joining(" ")), keyword));

        return nameMatch || addressMatch || categoryMatch || tagMatch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EateryAttributesContainsKeywordsPredicate // instanceof handles nulls
                && nameKeywords.equals(((EateryAttributesContainsKeywordsPredicate) other).nameKeywords)
                && addressKeywords.equals(((EateryAttributesContainsKeywordsPredicate) other).addressKeywords)
                && categoryKeywords.equals(((EateryAttributesContainsKeywordsPredicate) other).categoryKeywords)
                && tagKeywords.equals(((EateryAttributesContainsKeywordsPredicate) other).tagKeywords));
    }
}
