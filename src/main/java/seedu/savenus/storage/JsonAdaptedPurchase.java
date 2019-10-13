package seedu.savenus.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.TimeOfPurchase;

/**
 * Jackson-friendly version of {@link Purchase}.
 */
class JsonAdaptedPurchase {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Purchase's %s field is missing!";

    private final String purchasedFoodName;
    private final String purchasedFoodPrice;
    private final String timeOfPurchaseInMillisSinceEpochString;

    /**
     * Constructs a {@code JsonAdaptedPurchase} with the given Purchase details.
     */
    @JsonCreator
    public JsonAdaptedPurchase(@JsonProperty("purchasedFoodName") String purchasedFoodName,
                               @JsonProperty("purchasedFoodPrice") String purchasedFoodPrice,
                               @JsonProperty("timeOfPurchaseInMillisSinceEpochString")
                                              String timeOfPurchaseInMillisSinceEpochString) {
        this.purchasedFoodName = purchasedFoodName;
        this.purchasedFoodPrice = purchasedFoodPrice;
        this.timeOfPurchaseInMillisSinceEpochString = timeOfPurchaseInMillisSinceEpochString;
    }

    /**
     * Converts a given {@code Purchase} into this class for Jackson use.
     */
    public JsonAdaptedPurchase(Purchase source) {
        this.purchasedFoodName = source.getPurchasedFoodName().toString();
        this.purchasedFoodPrice = source.getPurchasedFoodPrice().toString();
        this.timeOfPurchaseInMillisSinceEpochString = Long.toString(source.getTimeOfPurchaseInMillisSinceEpoch());
    }

    /**
     * Converts this Jackson-friendly adapted Purchase object into the model's {@code Purchase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Purchase.
     */
    public Purchase toModelType() throws IllegalValueException {

        if (purchasedFoodName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(purchasedFoodName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(purchasedFoodName);

        if (purchasedFoodPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(purchasedFoodPrice)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(purchasedFoodPrice);

        if (timeOfPurchaseInMillisSinceEpochString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeOfPurchase.class.getSimpleName()));
        }
        if (!TimeOfPurchase.isValidTimeOfPurchase(timeOfPurchaseInMillisSinceEpochString)) {
            throw new IllegalValueException(TimeOfPurchase.MESSAGE_CONSTRAINTS);
        }
        final TimeOfPurchase modelTimeOfPurchase = new TimeOfPurchase(
                Long.parseLong(timeOfPurchaseInMillisSinceEpochString));

        return new Purchase(modelName, modelPrice, modelTimeOfPurchase);
    }
}
