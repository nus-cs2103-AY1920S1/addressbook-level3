package seedu.guilttrip.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.util.CategoryType;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    public static final String WRONG_CATEGORY_TYPE_MESSAGE_FORMAT = "Category Type of %s is not Income or Expense!";
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Category's %s field is missing!";

    private final String categoryName;
    private final String categoryType;
    /**
     * Constructs a {@code JsonAdaptedCategory} with the given {@code categoryName, @code categoryType}.
     */
    @JsonCreator
    public JsonAdaptedCategory(@JsonProperty("categoryName") String categoryName,
                               @JsonProperty("categoryType") String categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    /**
     * Converts a given {@code Category} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        categoryName = source.getCategoryName();
        categoryType = source.getCategoryType().getCatType();
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Category toModelType() throws IllegalValueException {
        if (categoryName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "category Name"));
        }
        if (categoryType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "category Type"));
        }

        if (!Category.isValidCategoryType(categoryType)) {
            throw new IllegalValueException(String.format(WRONG_CATEGORY_TYPE_MESSAGE_FORMAT, categoryName));
        }
        CategoryType categoryTyp = CategoryType.parse(categoryType);
        return new Category(categoryName, categoryTyp);
    }

}
