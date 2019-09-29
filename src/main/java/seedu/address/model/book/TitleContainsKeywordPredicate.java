package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TitleContainsKeywordPredicate implements Predicate<Book> {
    private final List<String> keywords;

    public TitleContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getTitle().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((TitleContainsKeywordPredicate) other).keywords)); // state check
    }

}
