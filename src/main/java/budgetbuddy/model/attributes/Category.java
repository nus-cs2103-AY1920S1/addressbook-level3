package budgetbuddy.model.attributes;

import static budgetbuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Stub class to represent a Category of a Transaction.
 */
public class Category {
    public static final int MAX_LENGTH = 180;

    public static final String MESSAGE_CONSTRAINTS =
            "Category should not be null or more than " + MAX_LENGTH + " characters.";


    private String category;

    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CONSTRAINTS);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public static boolean isValidCategory(String category) {
        return category != null && category.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return category;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Category && category.equals(((Category) other).category));
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }
}
