package seedu.savenus.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.TimeOfPurchase;

/**
 * Jackson-friendly version of {@link Purchase}.
 */
class JsonAdaptedPurchase {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Purchase's %s field is missing!";

    private final JsonAdaptedFood purchasedFood;
    private final String timeOfPurchaseInMillisSinceEpochString;

    /**
     * Constructs a {@code JsonAdaptedPurchase} with the given Purchase details.
     */
    @JsonCreator
    public JsonAdaptedPurchase(@JsonProperty("purchasedFood") JsonAdaptedFood purchasedFood,
                               @JsonProperty("timeOfPurchaseInMillisSinceEpochString")
                                              String timeOfPurchaseInMillisSinceEpochString) {
        this.purchasedFood = purchasedFood;
        this.timeOfPurchaseInMillisSinceEpochString = timeOfPurchaseInMillisSinceEpochString;
    }

    /**
     * Converts a given {@code Purchase} into this class for Jackson use.
     */
    public JsonAdaptedPurchase(Purchase source) {
        this.purchasedFood = new JsonAdaptedFood(source.getPurchasedFood());
        this.timeOfPurchaseInMillisSinceEpochString = Long.toString(source.getTimeOfPurchaseInMillisSinceEpoch());
    }

    /**
     * Converts this Jackson-friendly adapted Purchase object into the model's {@code Purchase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Purchase.
     */
    public Purchase toModelType() throws IllegalValueException {

        if (purchasedFood == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName()));
        }
        final Food modelPurchasedFood = purchasedFood.toModelType();

        if (timeOfPurchaseInMillisSinceEpochString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeOfPurchase.class.getSimpleName()));
        }
        if (!TimeOfPurchase.isValidTimeOfPurchase(timeOfPurchaseInMillisSinceEpochString)) {
            throw new IllegalValueException(TimeOfPurchase.MESSAGE_CONSTRAINTS);
        }
        final TimeOfPurchase modelTimeOfPurchase = new TimeOfPurchase(timeOfPurchaseInMillisSinceEpochString);

        return new Purchase(modelPurchasedFood, modelTimeOfPurchase);
    }
}
