package seedu.ichifund.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's category in the transaction recorder.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class Category implements Comparable<Category> {

    public static final String MESSAGE_CONSTRAINTS =
            "Categories should only up to 50 alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]{0,49}";
    public static final Category CATEGORY_ALL = new Category();
    public static final Category CATEGORY_DEFAULT = new Category("Uncategorised");

    public final String category;

    private Category() {
        this.category = "!all";
    }

    /**
     * Constructs a {@code Category}.
     *
     * @param category A valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CONSTRAINTS);
        this.category = category;
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return category;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && category.toLowerCase() // case-insensitive check
                .equals(((Category) other).category.toLowerCase()));
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }

    @Override
    public int compareTo(Category other) {
        return toString().compareTo(other.toString());
    }
}

