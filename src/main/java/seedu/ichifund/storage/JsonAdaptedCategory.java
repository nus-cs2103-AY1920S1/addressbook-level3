package seedu.ichifund.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.transaction.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    private final String category;

    /**
     * Constructs a {@code JsonAdaptedCategory} with the given {@code category}.
     */
    @JsonCreator
    public JsonAdaptedCategory(String category) {
        this.category = category;
    }

    /**
     * Converts a given {@code Category} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        this.category = source.category;
    }

    @JsonValue
    public String getCategory() {
        return this.category;
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted category.
     */
    public Category toModelType() throws IllegalValueException {
        if (!Category.isValidCategory(this.category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(this.category);
    }

}
