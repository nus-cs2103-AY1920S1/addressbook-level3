package seedu.address.model.binitem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;
import java.util.Objects;

/**
 * Wrapper class that wraps up Person and Policy objects when moved to the bin.
 */
public class BinItem {

    public static final int TIME_TO_LIVE = 30;

    private final Binnable item;
    private final Calendar dateDeleted;
    private final Calendar expiryDate;

    /**
     * Takes in a Binnable object. {@code item} must not be null.
     */
    public BinItem(Binnable item) {
        requireNonNull(item);
        this.item = item;
        this.dateDeleted = Calendar.getInstance();
        this.expiryDate = generateExpiryDate();
    }

    /**
     * Takes in a Binnable object. {@code item} must not be null.
     */
    public BinItem(Binnable item, Calendar dateDeleted, Calendar expiryDate) {
        requireAllNonNull(item, dateDeleted, expiryDate);
        this.item = item;
        this.dateDeleted = dateDeleted;
        this.expiryDate = expiryDate;
    }

    private Calendar generateExpiryDate() {
        Calendar expiryDate = Calendar.getInstance();
        expiryDate.add(Calendar.DAY_OF_YEAR , TIME_TO_LIVE);
        return expiryDate;
    }

    /**
     * Returns true if BinItem is expired, false otherwise.
     */
    public boolean isExpired() {
        Calendar currentDate = Calendar.getInstance();
        return currentDate.compareTo(expiryDate) > 0;
    }

    public Binnable getItem() {
        return item;
    }

    public Calendar getDateDeleted() {
        return dateDeleted;
    }

    public Calendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns true if both BinItems have the same item, dateDeleted and expiryDate.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BinItem)) {
            return false;
        }

        BinItem otherItem = (BinItem) other;
        return otherItem.getItem().equals(getItem())
            && otherItem.getDateDeleted().equals(getDateDeleted())
            && otherItem.getExpiryDate().equals(getExpiryDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, dateDeleted, expiryDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getItem().toString())
            .append(" Date deleted: ")
            .append(getDateDeleted())
            .append(" Expiry Date: ")
            .append(getExpiryDate());
        return builder.toString();
    }
}
