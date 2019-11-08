package seedu.guilttrip.testutil;

import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.util.CategoryType;

/**
 * A utility class to help with building Category objects.
 */
public class CategoryBuilder {

    public static final CategoryType DEFAULT_CATEGORY_TYPE = CategoryType.EXPENSE;
    public static final String DEFAULT_CATEGORY_NAME = "Food";

    private String categoryName;
    private CategoryType categoryType;

    public CategoryBuilder() {
        this.categoryType = DEFAULT_CATEGORY_TYPE;
        this.categoryName = DEFAULT_CATEGORY_NAME;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public CategoryBuilder(Category categoryToCopy) {
        this.categoryType = categoryToCopy.getCategoryType();
        this.categoryName = categoryToCopy.getCategoryName();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public CategoryBuilder withCatType(String desc) {
        this.categoryType = CategoryType.parse(desc);
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
        return new Category(categoryName, categoryType);
    }

}
