package seedu.address.model.phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's brand in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidBrand(String)}
 */
public class Brand implements Cloneable {

    public static final String MESSAGE_CONSTRAINTS = "Brands can take any values, and should not be blank";

    /*
     * The first character of the brand must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Brand}.
     *
     * @param brand A valid brand.
     */
    public Brand(String brand) {
        requireNonNull(brand);
        checkArgument(isValidBrand(brand), MESSAGE_CONSTRAINTS);
        value = brand;
    }

    /**
     * Returns true if a given string is a valid brand.
     */
    public static boolean isValidBrand(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Brand // instanceof handles nulls
                && value.equals(((Brand) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    protected Object clone() {
        return new Brand(new String(value));
    }

}
