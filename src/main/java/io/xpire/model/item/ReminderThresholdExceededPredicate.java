package io.xpire.model.item;

import java.util.function.Predicate;

import io.xpire.commons.util.DateUtil;

//@@author xiaoyu-nus
/**
 * Tests if a {@code XpireItem}'s {@code ReminderThreshold} is exceeded given the current date.
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
