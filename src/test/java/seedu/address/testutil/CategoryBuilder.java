package seedu.address.testutil;

import seedu.address.model.entry.Category;

public class CategoryBuilder {

    public static final String DEFAULT_CATEGORY_TYPE = "Expense";
    public static final String DEFAULT_CATEGORY_NAME = "Gardening";

    private String categoryName;
    private String categoryType;

    public CategoryBuilder() {
        this.categoryType = DEFAULT_CATEGORY_TYPE;
        this.categoryName = DEFAULT_CATEGORY_NAME;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public CategoryBuilder(Category categoryToCopy) {
        categoryType = categoryToCopy.getCategoryType();
        categoryName = categoryToCopy.getCategoryName();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public CategoryBuilder withCatType(String desc) {
        this.categoryType = desc;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public CategoryBuilder withCatName(String catName) {
        this.categoryName = catName;
        return this;
    }

    public Category build() {
        return new Category(categoryType, categoryName);
    }

}
