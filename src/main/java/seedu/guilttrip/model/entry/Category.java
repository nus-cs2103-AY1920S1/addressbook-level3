package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

import seedu.guilttrip.model.util.CategoryType;

/**
 * Represents a entry's Category in the guilttrip.
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS_TYPE =
            "Category Type should only be either Expense or Income";

    public static final String MESSAGE_CONSTRAINTS_NAME_NOT_EMPTY =
            "Category Name must not be empty.";

    public static final String[] VALIDATIONLIST = new String[] {"Expense", "Income"};

    public final String categoryName;
    public final CategoryType categoryType;

    /**
     * Constructs a {@code Name}. Validates that the categoryType is Expense or Income, and that the category
     * is not in the existing lists if its a new Category to be added, and that it is in the existing list
     * if it's a Category created for Entry.
     *
     * @param categoryName A valid  category name.
     * @param categoryType A valid category type.
     */
    public Category(String categoryName, String categoryType) {
        requireNonNull(categoryName);
        checkArgument(isValidCategoryType(categoryType), MESSAGE_CONSTRAINTS_TYPE);
        checkArgument(isNotEmptyCategoryName(categoryName), MESSAGE_CONSTRAINTS_NAME_NOT_EMPTY);
        this.categoryName = categoryName;
        this.categoryType = CategoryType.parse(categoryType);
    }

    public Category(String categoryName, CategoryType categoryType) {
        requireNonNull(categoryName);
        checkArgument(isNotEmptyCategoryName(categoryName), MESSAGE_CONSTRAINTS_NAME_NOT_EMPTY);
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getCategoryType() {
        return this.categoryType.getCatType();
    }

    /**
     * Returns the truth value for whether the categoryType specified is in the Validation List.
     */
    public static boolean isValidCategoryType(String categoryType) {
        return Arrays.stream(VALIDATIONLIST).anyMatch(t -> t.equalsIgnoreCase(categoryType));
    }

    public static boolean isNotEmptyCategoryName(String categoryName) {
        return !categoryName.trim().isEmpty();
    }

    /**
     * Returns the truth value for whether the category to be edited is in the same category as the original.
     */
    public boolean isSameCategory(Category otherCategory) {
        if (otherCategory == this) {
            return true;
        }

        return otherCategory != null
                && this.categoryName.equals(otherCategory.categoryName);
    }

    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && categoryName.equalsIgnoreCase(((Category) other).categoryName) // state check
                && categoryType.getCatType().equalsIgnoreCase(((Category) other).categoryType.getCatType()));
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

}
