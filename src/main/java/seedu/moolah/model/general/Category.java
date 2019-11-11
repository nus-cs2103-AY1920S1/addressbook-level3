package seedu.moolah.model.general;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Category in the MooLah.
 * Guarantees: immutable; name is valid as declared in {@link #isValidCategoryName(String)}
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS =
            "Category should only be one of the following: FOOD, TRAVEL, TRANSPORT, SHOPPING, UTILITIES, "
                    + "HEALTHCARE, ENTERTAINMENT, EDUCATION, OTHERS.";

    private static List<String> validCategories = List.of("FOOD", "TRAVEL", "TRANSPORT", "SHOPPING", "UTILITIES",
            "HEALTHCARE", "ENTERTAINMENT", "EDUCATION", "OTHERS");

    private static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private final String categoryName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param categoryName A valid category name.
     */
    public Category(String categoryName) {
        requireNonNull(categoryName);
        checkArgument(isValidCategoryName(categoryName), MESSAGE_CONSTRAINTS);
        this.categoryName = categoryName.toUpperCase();
    }

    //should be converted to Enum for better code quality
    public String getCategoryName() {
        return categoryName;
    }

    public static int indexOfInList(Category test) {
        return validCategories.indexOf(test.categoryName.toUpperCase());
    }


    /**
     * Returns true if a given string is a valid category name.
     */
    public static boolean isValidCategoryName(String test) {
        return test.matches(VALIDATION_REGEX) && validCategories.contains(test.toUpperCase());
    }

    public static List<Category> getValidCategories() {
        return validCategories
                .stream()
                .map(Category::new)
                .collect(Collectors.toList());
    }

    public static int getNumValidCategory() {
        return getValidCategories().size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && categoryName.equals(((Category) other).categoryName)); // state check
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return categoryName;
    }

}
