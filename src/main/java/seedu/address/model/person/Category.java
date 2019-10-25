package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

public class Category {

    public static final String MESSAGE_CONSTRAINTS_TYPE =
            "Category Type should only be either Expense or Income";

    public static final String MESSAGE_CONSTRAINTS_NOT_IN_LIST =
            "The category is not in the existing list of categories. Add a new Category through AddCategory Command. ";

    public static final String MESSAGE_CONSTRAINTS_IN_LIST =
            "The category is already in the existing list of categories. ";

    public static final String[] VALIDATIONLIST = new String[]{"Expense", "Income"};

    public final String categoryName;
    public final String categoryType;
    public final boolean isNewCategory;
    /**
     * Constructs a {@code Name}. Validates that the categoryType is Expense or Income, and that the category is not in the
     * existing lists if its a new Category to be added, and that it is in the existing list if it's a Category created for Entry.
     *
     * @param desc A valid name.
     */
    public Category(String categoryName, String categoryType, boolean isNewCategory) {
        requireNonNull(categoryName);
        checkArgument(isValidCategoryType(categoryType), MESSAGE_CONSTRAINTS_TYPE);
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        if (isNewCategory == true) {
            checkArgument(!isValidCategory(categoryName, categoryType), MESSAGE_CONSTRAINTS_NOT_IN_LIST);
        } else {
            checkArgument(isValidCategory(categoryName, categoryType), MESSAGE_CONSTRAINTS_IN_LIST);
        }
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCategory(String categoryName, String categoryType) {
        return CategoryList.isValidCategory(categoryName, categoryType);
    }


    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCategoryType(String test, String categoryType) {
        return Arrays.stream(VALIDATIONLIST).anyMatch(t -> t.equalsIgnoreCase(test));
    }

    public boolean isSameCategory(Category otherCategory) {
        if (otherCategory == this) {
            return true;
        }

        return otherCategory != null
                && otherCategory.categoryName().equals(otherCategory.categoryName);
    }

    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && categoryName.equals(((Category) other).categoryName)); // state check
                && categoryType.equals(((Category) other).categoryType)); // state check
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

}
