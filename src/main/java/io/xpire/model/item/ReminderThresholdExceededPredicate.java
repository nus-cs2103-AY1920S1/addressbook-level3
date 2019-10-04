package io.xpire.model.item;

import java.util.function.Predicate;

import io.xpire.commons.util.DateUtil;

/**
 * Tests that a {@code Item}'s {@code ExpiryDate} falls within the date given.
 */
public class ReminderThresholdExceededPredicate implements Predicate<Item> {

    @Override
    public boolean test(Item item) {
        return DateUtil.isWithinRange(item.getReminderThreshold().getValue(),
                DateUtil.getCurrentDate(),
                item.getExpiryDate().getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof ReminderThreshold;
        }
    }
}
