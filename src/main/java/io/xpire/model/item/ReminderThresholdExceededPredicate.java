package io.xpire.model.item;

import java.util.function.Predicate;

import io.xpire.commons.util.DateUtil;

/**
 * Tests that a {@code XpireItem}'s {@code ExpiryDate} falls within the date given.
 */
public class ReminderThresholdExceededPredicate implements Predicate<XpireItem> {

    @Override
    public boolean test(XpireItem xpireItem) {
        return DateUtil.isWithinRange(xpireItem.getReminderThreshold().getValue(),
                DateUtil.getCurrentDate(),
                xpireItem.getExpiryDate().getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof ReminderThresholdExceededPredicate;
        }
    }
}
