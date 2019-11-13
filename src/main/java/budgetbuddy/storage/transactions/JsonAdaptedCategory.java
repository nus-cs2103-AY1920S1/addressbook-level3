package budgetbuddy.storage.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.attributes.Category;

/**
 * Jackson-friendly version of {@link budgetbuddy.model.attributes.Category}.
 */
public class JsonAdaptedCategory {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Category's %s field is missing!";

    private final String category;

    /**
     * Constructs a {@code JsonAdaptedCategory} with the given category string.
     */
    @JsonCreator
    public JsonAdaptedCategory(@JsonProperty("category") String category) {
        this.category = category;
    }

    /**
     * Converts a given {@code Category} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        this.category = source.getCategory();
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted category.
     */
    public Category toModelType() throws IllegalValueException {
        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "category"));
        }
        if (!Category.isValidCategory(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }

        return new Category(category);
    }
}
