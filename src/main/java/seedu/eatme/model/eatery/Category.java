package seedu.eatme.model.eatery;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.util.AppUtil.checkArgument;

import java.util.HashMap;

/**
 * Represents an Eatery's category in the EatMe application.
 */
public class Category implements Comparable<Category> {

    public static final String MESSAGE_CONSTRAINTS =
            "Categories should only contain alphabets and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "([A-Za-z]\\w+[ A-Za-z]*|^[A-Za-z]+)";

    private static int categoryId = -1;
    private static HashMap<String, Category> categories = new HashMap<String, Category>();

    private int id;
    private String name;

    /**
     * Constructs a {@code Category}.
     *
     * @param name A valid category name.
     */
    public Category(String name) {
        requireNonNull(name);
        checkArgument(isValidCategory(name), MESSAGE_CONSTRAINTS);
        categoryId = categoryId + 1;
        this.id = categoryId;
        this.name = name;
    }

    /**
     * Returns a new Category object if Category with the same name doesn't exist,
     * else it returns the existing Category object.
     */
    public static Category create(String name) {
        Category category = categories.get(name);

        if (category == null) {
            Category newCategory = new Category(name);
            categories.put(newCategory.getName(), newCategory);
            return newCategory;
        } else {
            return category;
        }
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Category)
                && name.equals(((Category) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Category otherCategory) {
        return name.compareTo(otherCategory.getName());
    }
}
