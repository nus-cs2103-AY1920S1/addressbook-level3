package seedu.moolah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.moolah.commons.exceptions.IllegalValueException;
import seedu.moolah.model.general.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    private final String categoryName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code categoryName}.
     */
    @JsonCreator
    public JsonAdaptedCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        categoryName = source.getCategoryName();
    }

    @JsonValue
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Tag} object.
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
