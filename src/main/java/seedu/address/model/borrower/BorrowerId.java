package seedu.address.model.borrower;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Borrower's ID in borrowers records
 */
public class BorrowerId {

    public static final String MESSAGE_CONSTRAINTS = "Borrower's ID should start with prefix \"K\", "
            + "followed by 4 digits. They should be unique.";
    private static final String VALIDATION_REGEX = "K\\d{4}";
    public final String value;

    public BorrowerId(String borrowerId) {
        requireNonNull(borrowerId);
        checkArgument(isValidBorrowerId(borrowerId), MESSAGE_CONSTRAINTS);
        value = borrowerId;
    }

    public static boolean isValidBorrowerId(String borrowerId) {
        return borrowerId.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BorrowerId // instanceof handles nulls
                && value.equals(((BorrowerId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
