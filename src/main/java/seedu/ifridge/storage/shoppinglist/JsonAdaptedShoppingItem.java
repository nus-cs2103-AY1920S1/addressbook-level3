package seedu.ifridge.storage.shoppinglist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.food.Amount;
//import seedu.address.model.food.ExpiryDate;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * Jackson-friendly version of {@link ShoppingItem}.
 */
public class JsonAdaptedShoppingItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Shopping Item's %s field is missing!";

    private final String name;
    private final String amount;
    private final boolean urgent;
    private final boolean bought;

    /**
     * Constructs a {@code JsonAdaptedShoppingItem} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedShoppingItem(
            @JsonProperty("name") String name,
            @JsonProperty("amount") String amount,
            @JsonProperty("bought") boolean bought,
            @JsonProperty("urgent") boolean urgent) {
        this.name = name;
        this.amount = amount;
        this.bought = bought;
        this.urgent = urgent;
    }

    /**
     * Converts a given {@code ShoppingItem} into this class for Jackson use.
     */
    public JsonAdaptedShoppingItem(ShoppingItem source) {
        name = source.getName().fullName;
        amount = source.getAmount().fullAmt;
        bought = source.isBought();
        urgent = source.isUrgent();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code ShoppingItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public ShoppingItem toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final Amount modelAmount = new Amount(amount);
        ShoppingItem result = new ShoppingItem(modelName, modelAmount);
        result = result.setBought(bought);
        result = result.setUrgent(urgent);
        return result;
    }

}
