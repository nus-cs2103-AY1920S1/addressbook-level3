package io.xpire.model.item;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.commons.util.CollectionUtil.stringifyCollection;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import io.xpire.commons.util.StringUtil;

//@@author JermyTan
/**
 * Tests if an {@code Item}'s {@code Name} or {@code Tag} matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Item> {
    private final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        requireAllNonNull(keywords);
        Collections.sort(keywords);
        this.keywords = keywords;
    }

    /**
     * Tests if an item contains any of the keywords in its name or tag(s).
     *
     * @param item Item.
     * @return {@code true} if item contains any of the keywords else {@code false}.
     */
    @Override
    public boolean test(Item item) {
        requireNonNull(item);

        boolean nameContainsKeywords;
        boolean tagsContainsKeywords;

        for (String keyword: this.keywords) {
            nameContainsKeywords = StringUtil.containsPhraseIgnoreCase(item.getName().toString(), keyword);
            tagsContainsKeywords = keyword.startsWith("#")
                    && keyword.length() > 1
                    && new HashSet<>(stringifyCollection(
                            item.getTags(), tag -> tag.substring(1))
                    )
                    .contains(keyword.substring(1));
            if (nameContainsKeywords || tagsContainsKeywords) {
                return true;
            }
        }
        return false;
    }

    public List<String> getKeywords() {
        return this.keywords;
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
