package budgetbuddy.model.attributes;

import static budgetbuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Stub class to represent a Category of a Transaction.
 */
public class Category {
    public static final String MESSAGE_CONSTRAINTS =
            "Category should not be empty.";

    private String category;

    public Category(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.category = description;
    }

    public String getCategory() {
        return category;
    }

    public static boolean isValidDescription(String description) {
        return description != null;
    }

    @Override
    public String toString() {
        return category;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Category && category == ((Category) other).category);
    }

}
