package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Transaction}'s {@code Tag} matches the {@code Tag} given.
 */
public class TransactionContainsTagsPredicate implements Predicate<Transaction> {

    private final List<String> keyTags;

    public TransactionContainsTagsPredicate(List<String> keyTags) {
        this.keyTags = keyTags;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keyTags.stream()
                .anyMatch(keyTag -> isTagInsideTags(transaction, keyTag));
    }

    /**
     * Checks if a {@code Tag} exists in {@code Transaction}'s set of {@code Tag}s.
     *
     * @param transaction {@code Transaction} to be checked.
     * @param keyTag      Tag to be found in {@code Transaction}
     */
    private boolean isTagInsideTags(Transaction transaction, String keyTag) {
        return transaction
                .getTags()
                .stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(), keyTag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionContainsTagsPredicate // instanceof handles nulls
                && keyTags.equals(((TransactionContainsTagsPredicate) other).keyTags)); // state check
    }
}
