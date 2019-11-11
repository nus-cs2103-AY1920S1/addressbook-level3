package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import io.xpire.commons.util.AppUtil;
import io.xpire.commons.util.StringUtil;


/**
 * Represents an XpireItem's reminder threshold.
 * Guarantees: immutable; is valid as declared in {@link #isValidReminderThreshold(String)}.
 */
public class ReminderThreshold {

    public static final String MESSAGE_CONSTRAINTS = "Invalid value! Reminder threshold should be an unsigned"
            + "  non-negative integer no greater than 36500. \n";
    public static final String MESSAGE_CONSTRAINTS_LOWER = "Reminder before 1/10/2019 are not accepted in Json file"
            + " to prevent outdated reminder.";
    public static final String DEFAULT_THRESHOLD = "0";

    public static final int MAX_VALUE = 36500;

    private final int reminderThreshold;

    /**
     * Constructs a {@code ReminderThreshold}.
     *
     * @param reminderThreshold A valid reminder threshold.
     */
    public ReminderThreshold(String reminderThreshold) {
        requireNonNull(reminderThreshold);
        AppUtil.checkArgument(isValidReminderThreshold(reminderThreshold), MESSAGE_CONSTRAINTS);
        this.reminderThreshold = Integer.parseInt(reminderThreshold);
    }

    /**
     * Returns true if a given integer is a valid reminder threshold.
     */
    public static boolean isValidReminderThreshold(String test) {
        return StringUtil.isNonNegativeInteger(test)
                && (!StringUtil.isExceedingMaxValue(test, MAX_VALUE));
    }

    /**
     * Returns true if a given integer is a valid reminder threshold and is smaller than given days.
     */
    public static boolean isValidReminderThreshold(String test, String days) {
        int threshold = Integer.parseInt(test);
        long remainingDays = Long.parseLong(days);
        return isValidReminderThreshold(test) && threshold <= remainingDays;
    }

    public int getValue() {
        return this.reminderThreshold;
    }

    public boolean isDefault() {
        return this.reminderThreshold == 0;
    }

    @Override
    public String toString() {
        return "" + this.reminderThreshold;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ReminderThreshold)) {
            return false;
        } else {
            ReminderThreshold other = (ReminderThreshold) obj;
            return this.reminderThreshold == other.reminderThreshold;
        }
    }

    @Override
    public int hashCode() {
        return this.reminderThreshold;
    }
}
