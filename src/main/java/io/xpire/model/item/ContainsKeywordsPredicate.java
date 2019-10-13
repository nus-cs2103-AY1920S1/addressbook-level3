package io.xpire.model.item;

import static io.xpire.commons.util.CollectionUtil.stringifyCollection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import io.xpire.commons.util.StringUtil;

/**
 * Tests that a {@code Item}'s {@code Name} matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Item> {
    private final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        Collections.sort(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        boolean keywordsInName;
        boolean keywordsInTags;

        for (String keyword: this.keywords) {
            keywordsInName = StringUtil.containsPhraseIgnoreCase(item.getName().toString(), keyword);
            keywordsInTags = keyword.startsWith("#")
                    && keyword.length() > 1
                    && new HashSet<>(stringifyCollection(item.getTags(), tag -> tag.substring(1, tag.length()-1)))
                    .contains(keyword.substring(1));
            if (keywordsInName || keywordsInTags) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ContainsKeywordsPredicate)) {
            return false;
        } else {
            ContainsKeywordsPredicate other = (ContainsKeywordsPredicate) obj;
            return this.keywords.equals(other.keywords);
        }
    }

    @Override
    public int hashCode() {
        return this.keywords.hashCode();
    }
}
