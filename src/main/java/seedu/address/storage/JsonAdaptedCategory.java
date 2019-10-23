package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedCategory(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Category} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        tagName = source.categoryName;
    }

    @JsonValue
    public String getCategoryName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted category.
     */
    public Category toModelType() throws IllegalValueException {
        if (!Category.isValidCategoryName(tagName)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(tagName);
    }

}
