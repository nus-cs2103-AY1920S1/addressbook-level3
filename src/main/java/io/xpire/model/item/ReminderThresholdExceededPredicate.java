package io.xpire.model.item;

/**
 * Tests that a {@code Item}'s {@code ExpiryDate} falls within the date given.
 */
public class ReminderThresholdExceededPredicate extends CheckCommandPredicate {

    @Override
    public boolean test(Item item) {
        return item.isReminderThresholdExceeded();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderThresholdExceededPredicate); // state check
    }
}
