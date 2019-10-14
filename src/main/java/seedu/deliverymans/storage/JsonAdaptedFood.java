package seedu.deliverymans.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.addressbook.tag.Tag;

/**
 * Jackson-friendly version of {@link Food}.
 */
class JsonAdaptedFood {
//
//    private final String foodName;
//
//    /**
//     * Constructs a {@code JsonAdaptedFood} with the given {@code foodName}.
//     */
//    @JsonCreator
//    public JsonAdaptedFood(String foodName) {
//        this.foodName = foodName;
//    }
//
//    /**
//     * Converts a given {@code Food} into this class for Jackson use.
//     */
//    public JsonAdaptedFood(Food source) {
//        foodName = source.foodName;
//    }
//
//    @JsonValue
//    public String getTagName() {
//        return tagName;
//    }
//
//    /**
//     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
//     *
//     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
//     */
//    public Food toModelType() throws IllegalValueException {
//        if (!Tag.isValidTagName(tagName)) {
//            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
//        }
//        return new Tag(tagName);
//    }
}
