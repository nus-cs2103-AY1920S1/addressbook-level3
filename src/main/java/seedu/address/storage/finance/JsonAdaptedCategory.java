package seedu.address.storage.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.attributes.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    private final String catName;

    /**
     * Constructs a {@code JsonAdaptedCategory} with the given {@code catName}.
     */
    @JsonCreator
    public JsonAdaptedCategory(String catName) {
        this.catName = catName;
    }

    /**
     * Converts a given {@code Category} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        catName = source.catName;
    }

    @JsonValue
    public String getCatName() {
        return catName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Category toModelType() throws IllegalValueException {
        if (!Category.isValidCatName(catName)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(catName);
    }
}
