package seedu.address.model.binitem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Wrapper class that wraps up Person and Policy objects when moved to the bin.
 */
public class BinItem {

    public static final int TIME_TO_LIVE_AMOUNT = 10;
    public static final ChronoUnit TIME_TO_LIVE_UNIT = ChronoUnit.SECONDS;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy 'at' hh:mm a");

    private final Binnable item;
    private final LocalDateTime dateDeleted;
    private final LocalDateTime expiryDate;

    /**
     * Takes in a Binnable object. {@code item} must not be null.
     */
    public BinItem(Binnable item) {
        requireNonNull(item);
        this.item = item;
        this.dateDeleted = LocalDateTime.now();
        this.expiryDate = generateExpiryDate();
    }

    /**
     * Takes in a Binnable object. {@code item} must not be null.
     */
    public BinItem(Binnable item, LocalDateTime dateDeleted, LocalDateTime expiryDate) {
        requireAllNonNull(item, dateDeleted, expiryDate);
        this.item = item;
        this.dateDeleted = dateDeleted;
        this.expiryDate = expiryDate;
    }

    private LocalDateTime generateExpiryDate() {
        LocalDateTime expiryDate = LocalDateTime.now().plus(TIME_TO_LIVE_AMOUNT, TIME_TO_LIVE_UNIT);
        return expiryDate;
    }

    /**
     * Returns true if BinItem is expired, false otherwise.
     */
    public boolean isExpired() {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.compareTo(expiryDate) > 0;
    }

    public Binnable getItem() {
        return item;
    }

    public String getDateDeleted() {
        return dateDeleted.format(DATE_TIME_FORMATTER);
    }

    public String getExpiryDate() {
        return expiryDate.format(DATE_TIME_FORMATTER);
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
