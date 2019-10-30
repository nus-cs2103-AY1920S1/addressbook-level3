package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable.
 */
public class Credit {

    public static final String MESSAGE_CONSTRAINTS =
            "Credit cannot be out of the range of NUS permissible modular credit and it cannot be a negative number.";

    private final int credit;
    /**
     * Constructs a {@code Phone}.
     *
     * @param credit A valid phone number.
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
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidCredit(int credit) {
        int value = credit;
        if (value < 25 && value >= 2) {
            return true;
        } else {
            return false;
        }
    }
}
