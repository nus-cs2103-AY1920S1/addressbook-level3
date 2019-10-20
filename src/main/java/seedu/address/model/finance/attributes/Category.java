package seedu.address.model.finance.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Category in the finance log, a group of entries
 * associated with a similar purpose (e.g. for spending) or source (e.g. income).
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS = "Category names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String catName;

    /**
     * Constructs a {@code Category}.
     *
     * @param catName A valid category name.
     */
    public Category(String catName) {
        requireNonNull(catName);
        checkArgument(isValidCatName(catName), MESSAGE_CONSTRAINTS);
        this.catName = catName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidCatName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && catName.equals(((Category) other).catName)); // state check
    }

    @Override
    public int hashCode() {
        return catName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + catName + ']';
    }

}
