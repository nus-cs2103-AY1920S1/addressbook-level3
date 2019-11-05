package seedu.address.model.person;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_CONSTRAINTS =
            "Remarks should only contain alphanumeric characters and spaces, and it should not be blank";

    public final String value;

    /**
     * Constructs an {@code Remark}.
     */
    public Remark(String remark) {
        value = remark;
    }

    private Remark() {
        value = "";
    }

    /**
     * Static method to get an empty remark.
     */
    public static Remark emptyRemark() {
        return new Remark();
    }

    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
