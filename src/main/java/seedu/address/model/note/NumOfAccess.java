package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Note's number of access by the user in the note book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNumOfAccess(int)}
 */
public class NumOfAccess {

    public static final String MESSAGE_CONSTRAINTS = "NumOfAccess can take only positive integers,"
            + " and it should not be blank";

    public final int numOfAccess;

    /**
     * Constructs an {@code Description}.
     *
     * @param num A valid integer to represent the number of access to the note.
     */
    public NumOfAccess(int num) {
        requireNonNull(num);
        checkArgument(isValidNumOfAccess(num), MESSAGE_CONSTRAINTS);
        this.numOfAccess = num;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidNumOfAccess(int test) {
        return test >= 0;
    }

    public NumOfAccess update() {
        return new NumOfAccess(numOfAccess + 1);
    }

    @Override
    public String toString() {
        return numOfAccess + "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumOfAccess // instanceof handles nulls
                && numOfAccess == (((NumOfAccess) other).numOfAccess)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(numOfAccess);
    }

}
