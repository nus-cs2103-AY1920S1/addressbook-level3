package seedu.module.model.module.predicate;

import java.util.function.Predicate;

import seedu.module.model.module.Module;

/**
 * Tests that a {@code Module}'s title contains the sentence given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<Module> {
    private final String keyword;

    public TitleContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean test(Module module) {
        return module.getTitle().toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((TitleContainsKeywordsPredicate) other).keyword)); // state check
    }

}
