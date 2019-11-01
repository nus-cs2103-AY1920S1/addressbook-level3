package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Entry;

/**
 * Tests that a {@code Entry}'s {@code Amount} is larger than the given amount.
 */
public class EntryContainsAmountPredicate implements Predicate<Entry> {
    private final double amountContain;

    public EntryContainsAmountPredicate(double amountContain) {
        this.amountContain = amountContain;
    }

    @Override
    public boolean test(Entry entry) {
        return amountContain <= entry.getAmount().value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsAmountPredicate // instanceof handles nulls
                && amountContain == (((EntryContainsAmountPredicate) other).amountContain)); // state check
    }
}
