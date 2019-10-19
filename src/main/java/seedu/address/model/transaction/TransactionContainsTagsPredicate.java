package seedu.address.model.transaction;

import seedu.address.model.tag.Tag;

import java.util.function.Predicate;

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
        return transaction.getTags().contains(keyTag);

        // return keywords.stream()
        //         .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getTags(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionContainsTagsPredicate // instanceof handles nulls
                && keyTag.equals(((TransactionContainsTagsPredicate) other).keyTag)); // state check
    }
}
