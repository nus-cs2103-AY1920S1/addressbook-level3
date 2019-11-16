package seedu.ifridge.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;

/**
 * Jackson-friendly version of {@link TemplateItem}.
 */
class JsonAdaptedTemplateItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "TemplateItem's %s field is missing!";

    private final String name;
    private final String amount;

    /**
     * Constructs a {@code JsonAdaptedTemplateItem} with the given template item details.
     */
    @JsonCreator
    public JsonAdaptedTemplateItem(
            @JsonProperty("name") String name,
            @JsonProperty("amount") String amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Converts a given {@code TemplateItem} into this class for Jackson use.
     */
    public JsonAdaptedTemplateItem(TemplateItem source) {
        name = source.getName().fullName;
        amount = source.getAmount().fullAmt;
    }

    /**
     * Converts this Jackson-friendly adapted template item object into the model's {@code TemplateItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted template item.
     */
    public TemplateItem toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final Amount modelAmount = new Amount(amount);

        return new TemplateItem(modelName, modelAmount);
    }

}
