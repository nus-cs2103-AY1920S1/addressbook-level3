package io.xpire.model.item;

import java.util.function.Predicate;

import io.xpire.commons.util.DateUtil;

//@@author JermyTan
/**
 * Tests if a {@code XpireItem}'s {@code ExpiryDate} falls within the number of days given
 * from the current date.
 */
public class ExpiringSoonPredicate implements Predicate<XpireItem> {
    /** Offset range between 2 dates. */
    private final int days;

    /**
     * Public constructor for class.
     *
     * @param days Offset range between 2 dates.
     */
    public ExpiringSoonPredicate(int days) {
        this.days = days;
    }

    /**
     * Tests if the expiry date is within the specified number of days from the current date.
     *
     * @param xpireItem Item to be tested.
     * @return {@code true} if expiry date is within the specified number of days else {@code false}.
     */
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
