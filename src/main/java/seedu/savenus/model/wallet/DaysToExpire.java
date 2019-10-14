package seedu.savenus.model.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the number of days to budget expiration in the application.
 */
public class DaysToExpire {

    public static final String MESSAGE_CONSTRAINTS =
            "Budget Duration should be a positive integer";
    public static final String INTEGER_CONSTRAINTS =
            "Due to Integer limitations, this application will not accept Budget Duration of more than 365 days";
    public static final String VALIDATION_REGEX = "0|[1-9]\\d*$";

    private final IntegerProperty daysToExpireProperty;
    private LocalDateTime expirationDateTime;

    /**
     * Constructs a {@code daysToExpire}.
     *
     * @param newDaysToExpireString A new, valid daysToExpire string.
     */
    public DaysToExpire(String newDaysToExpireString) {
        requireNonNull(newDaysToExpireString);
        checkArgument(isValidDaysToExpire(newDaysToExpireString), MESSAGE_CONSTRAINTS);
        daysToExpireProperty = new SimpleIntegerProperty(Integer.parseInt(newDaysToExpireString));
        expirationDateTime = LocalDateTime.now().plusDays(Integer.parseInt(newDaysToExpireString));
    }

    /**
     * Update number of days left with respect to current time.
     */
    public void updateDaysToExpire() {
        daysToExpireProperty.set((int) LocalDateTime.now().until(expirationDateTime, ChronoUnit.DAYS) + 1);
    }

    /**
     * Returns true if a given string is a valid daysToExpire number.
     */
    public static boolean isValidDaysToExpire(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if this instance of {@code DaysToExpire} is out of bounds.
     */
    public boolean isOutOfBounds() {
        return (getDaysToExpire() > 365);
    }

    /**
     * Returns the {@code IntegerProperty} of this instance.
     */
    public IntegerProperty getDaysToExpireProperty() {
        return daysToExpireProperty;
    }

    /**
     * Returns the {@code int} value of this instance
     */
    public int getDaysToExpire() {
        return daysToExpireProperty.get();
    }

    /**
     * Replaces the old {@code DaysToExpire} with the new {@code DaysToExpire}.
     */
    public void setDaysToExpire(DaysToExpire newDaysToExpire) {
        daysToExpireProperty.setValue(newDaysToExpire.getDaysToExpire());
        expirationDateTime = newDaysToExpire.expirationDateTime;
    }

    @Override
    public String toString() {
        return String.format("%d days", getDaysToExpire());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DaysToExpire)) {
            return false;
        }

        DaysToExpire otherDaysToExpire = (DaysToExpire) other;
        return otherDaysToExpire.getDaysToExpire() == this.getDaysToExpire();
    }
}
