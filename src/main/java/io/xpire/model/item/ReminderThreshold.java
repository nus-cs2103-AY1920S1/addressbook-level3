package io.xpire.model.item;

import io.xpire.commons.util.AppUtil;

/**
 * Represents an Item's reminder threshold in the expiry date tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidReminderThreshold(String)}
 */
public class ReminderThreshold {

    public static final String MESSAGE_CONSTRAINTS =
            "Reminder threshold should be a non-negative integer.";

    private final int threshold;

    /**
     * Constructs a {@code ReminderThreshold}.
     *
     * @param threshold A valid reminder threshold.
     */
    public ReminderThreshold(String threshold) {
        AppUtil.checkArgument(isValidReminderThreshold(String.valueOf(threshold)), MESSAGE_CONSTRAINTS);
        this.threshold = Integer.parseInt(threshold);
    }

    /**
     * Returns true if a given integer is a valid reminder threshold.
     */
    public static boolean isValidReminderThreshold(String test) {
        int num = Integer.parseInt(test);
        try {
            if (num < 0) {
                throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public int getThreshold() {
        return threshold;
    }

    @Override
    public String toString() {
        return "Reminder threshold: " + this.threshold;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderThreshold // instanceof handles nulls
                && threshold == ((ReminderThreshold) other).getThreshold()); // state check
    }

    @Override
    public int hashCode() {
        return ("" + threshold).hashCode();
    }

}
