package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Category;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedCategory {

    public static final String WRONG_CATEGORY_TYPE_MESSAGE_FORMAT = "Category Type of %s is not Income or Expense!";

    private final String categoryName;
    private final String categoryType;
    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedCategory(@JsonProperty("categoryName") String categoryName,
                               @JsonProperty("categoryType") String categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        categoryName = source.categoryName;
        categoryType = source.categoryType;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Category toModelType() throws IllegalValueException {
        if (!Category.isValidCategoryType(categoryType)) {
            throw new IllegalValueException(String.format(WRONG_CATEGORY_TYPE_MESSAGE_FORMAT, categoryName));
        }
        return new Category(categoryName, categoryType);
    }

}
