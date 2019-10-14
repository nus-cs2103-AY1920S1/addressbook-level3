package seedu.address.model.visit;

/**
 * Represents a Visit's remark in the application.
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remarks can take any values.";

    public final String remark;

    /**
     * Constructs a {@code Name}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        this.remark = remark == null ? "" : remark;
    }

    @Override
    public String toString() {
        return remark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && remark.equals(((Remark) other).remark)); // state check
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }

}
