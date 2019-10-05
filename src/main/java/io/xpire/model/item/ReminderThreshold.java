package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import io.xpire.commons.util.AppUtil;
import io.xpire.commons.util.StringUtil;


/**
 * Represents an Item's reminder threshold.
 * Guarantees: immutable; is valid as declared in {@link #isValidReminderThreshold(String)}
 */
public class ReminderThreshold {

    public static final String MESSAGE_CONSTRAINTS = "Reminder threshold should be a non-negative integer.";

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
        return StringUtil.isNonNegativeInteger(test);
    }

    public int getValue() {
        return this.reminderThreshold;
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
