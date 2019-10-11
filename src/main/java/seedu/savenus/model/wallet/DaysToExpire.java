package seedu.savenus.model.wallet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of days to budget expiration in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDaysToExpire(String)}
 */
public class DaysToExpire {

    public static final String MESSAGE_CONSTRAINTS =
            "Number of days to budget expiration should be a positive integer";
    public static final String VALIDATION_REGEX = "^\\d+$";

    private final IntegerProperty daysToExpireProperty;
    private final LocalDateTime expirationDateTime;

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

        if (!(other instanceof RemainingBudget)) {
            return false;
        }

        DaysToExpire otherDaysToExpire = (DaysToExpire) other;
        return otherDaysToExpire.getDaysToExpire() == this.getDaysToExpire();
    }
}
