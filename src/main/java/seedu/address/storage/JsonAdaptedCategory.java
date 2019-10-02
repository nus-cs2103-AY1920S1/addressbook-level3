package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    private final String categoryName;

    /**
     * Constructs a {@code JsonAdaptedCategory} with the given {@code categoryName}.
     */
    @JsonCreator
    public JsonAdaptedCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Converts a given {@code Category} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        categoryName = source.categoryName;
    }

    @JsonValue
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted category.
     */
    public Category toModelType() throws IllegalValueException {
        if (!Category.isValidCategoryName(categoryName)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(categoryName);
    }

}
