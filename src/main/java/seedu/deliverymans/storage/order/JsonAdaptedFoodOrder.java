package seedu.deliverymans.storage.order;

import java.util.AbstractMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * Jackson-friendly version of Food in {@link Order}.
 */
public class JsonAdaptedFoodOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order food's %s field is missing!";

    private final String foodName;
    private final String quantity;

    /**
     * Constructs a {@code JsonAdaptedFood} with the given {@code foodName}.
     */
    @JsonCreator
    public JsonAdaptedFoodOrder(@JsonProperty("name") String foodName,
                                @JsonProperty("price") String quantity) {
        this.foodName = foodName;
        this.quantity = quantity;
    }


    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedFoodOrder(Map.Entry<Name, Integer> entry) {
        foodName = entry.getKey().fullName;
        quantity = entry.getValue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Map.Entry<Name, Integer> toModelType() throws IllegalValueException {
        if (foodName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(foodName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(foodName);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName()));
        }

        return new AbstractMap.SimpleEntry<Name, Integer>(modelName, Integer.parseInt(quantity));
    }
}
