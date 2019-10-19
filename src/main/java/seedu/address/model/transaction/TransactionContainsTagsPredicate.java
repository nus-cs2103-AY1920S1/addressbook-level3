package seedu.address.model.transaction;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Transaction}'s {@code Tag} matches the tag given.
 */
public class TransactionContainsTagsPredicate implements Predicate<Transaction> {

    private final Tag keyTag;

    public TransactionContainsTagsPredicate(Tag keyTag) {
        this.keyTag = keyTag;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction
                .getTags()
                .stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(), keyTag.getTagName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionContainsTagsPredicate // instanceof handles nulls
                && keyTag.equals(((TransactionContainsTagsPredicate) other).keyTag)); // state check
    }
}
