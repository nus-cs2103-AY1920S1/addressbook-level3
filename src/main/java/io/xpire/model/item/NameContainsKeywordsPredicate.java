package io.xpire.model.item;

import java.util.List;
import java.util.function.Predicate;

import io.xpire.commons.util.StringUtil;

/**
 * Tests that a {@code Item}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Item> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        return this.keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(item.getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof NameContainsKeywordsPredicate)) {
            return false;
        } else {
            NameContainsKeywordsPredicate other = (NameContainsKeywordsPredicate) obj;
            return this.keywords.equals(other.keywords);
        }
    }

    @Override
    public int hashCode() {
        return this.keywords.hashCode();
    }
}
