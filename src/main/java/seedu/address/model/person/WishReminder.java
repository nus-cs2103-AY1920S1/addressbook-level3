package seedu.address.model.person;

import java.time.LocalDate;

/**
 * Implements wish reminder
 */
public class WishReminder extends Reminder {
    private Wish wish;
    public WishReminder(String desc, Wish wish) {
        super(desc);
        this.wish = wish;
    }
    public Wish getWish() {
        return wish;
    }
    /**
     *checks status of reminder. i.e. should reminder trigger.
     */
    public void updateStatus() {
        LocalDate date = wish.getTime();
        super.setStatus(!LocalDate.now().isBefore(date));
    }

    public void setWish(Wish wish) {
        assert (wish.equals(wish));
        this.wish = wish;
    }
    @Override
    /**
     * Returns true if both WishReminders have all identity fields that are the same.
     * @param otherReminder WishReminder to compare to
     * @return boolean
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }
        if (!(otherReminder instanceof WishReminder)) {
            return false;
        }
        WishReminder otherWishReminder = (WishReminder) otherReminder;
        return otherWishReminder != null
                && otherWishReminder.getMessage().equals(getMessage())
                && otherWishReminder.getWish().equals(getWish());
    }
}
