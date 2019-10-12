package seedu.savenus.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's category in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class Category implements Field {

    public static final String MESSAGE_CONSTRAINTS =
            "Categories should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String category;

    /**
     * Constructs a {@code Category}.
     *
     * @param name A valid name.
     */
    public Category(String name) {
        requireNonNull(name);
        checkArgument(isValidCategory(name), MESSAGE_CONSTRAINTS);
        category = name;
    }

    /**
     * Returns true if a given string is a valid categroy.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Gets the field as a String.
     * @return a String representation of the field.
     */
    public String getField() {
        return this.toString();
    }

    @Override
    public String toString() {
        return category;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && category.equals(((Category) other).category)); // state check
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }

    @Override
    public int compareTo(Field other) {
        Category otherCategory = (Category) other;
        if (otherCategory == null) {
            return 1;
        } else {
            return this.getField().compareTo(otherCategory.getField());
        }
    }
}
