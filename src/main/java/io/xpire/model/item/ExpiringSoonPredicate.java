package io.xpire.model.item;

import java.util.function.Predicate;

import io.xpire.commons.util.DateUtil;

/**
 * Tests that a {@code XpireItem}'s {@code ExpiryDate} falls within the date given.
 */
public class ExpiringSoonPredicate implements Predicate<XpireItem> {
    private final int days;

    public ExpiringSoonPredicate(int days) {
        this.days = days;
    }

    @Override
    public boolean test(XpireItem xpireItem) {
        return DateUtil.isWithinRange(this.days, DateUtil.getCurrentDate(), xpireItem.getExpiryDate().getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ExpiringSoonPredicate)) {
            return false;
        } else {
            ExpiringSoonPredicate other = (ExpiringSoonPredicate) obj;
            return this.days == other.days;
        }
    }

    @Override
    public int hashCode() {
        return this.days;
    }
}
