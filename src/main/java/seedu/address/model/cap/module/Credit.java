package seedu.address.model.cap.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's Credit in the Modulo Grades Tracker.
 * Guarantees: immutable.
 */
public class Credit {

    public static final String MESSAGE_CONSTRAINTS =
        "Credit cannot be out of the range of NUS permissible "
            + "modular credit of 2 and 23 and it cannot be a negative number.";

    private final int credit;
    /**
     * Constructs a {@code Credit}.
     *
     * @param credit A valid credit.
     */
    public Credit(int credit) {
        requireNonNull(credit);
        checkArgument(isValidCredit(credit), MESSAGE_CONSTRAINTS);
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Credit // instanceof handles nulls
                && this.credit == credit); // state check
    }

    @Override
    public String toString() {
        return String.valueOf(credit);
    }

    /**
     * Returns true if a given string is a valid credit.
     */
    public static boolean isValidCredit(int credit) throws NullPointerException {
        requireNonNull(credit);
        int value = credit;
        if (value < 23 && value >= 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid credit.
     */
    public static boolean isValidCredit(String credit) throws NumberFormatException {
        try {
            int value = Integer.parseInt(credit);
            if (value < 23 && value >= 2) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
