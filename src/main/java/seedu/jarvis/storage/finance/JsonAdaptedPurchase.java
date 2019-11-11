package seedu.jarvis.storage.finance;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.finance.purchase.PurchaseDescription;
import seedu.jarvis.model.finance.purchase.PurchaseMoneySpent;
import seedu.jarvis.storage.JsonAdapter;

/**
 * Jackson-friendly version of {@link Purchase}.
 */
public class JsonAdaptedPurchase implements JsonAdapter<Purchase> {

    public static final String MESSAGE_INVALID_ATTRIBUTES = "Invalid purchase attributes.";

    private final String description;
    private final String amount;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedPurchase} with the given purchase details.
     *
     * @param description {@code PurchaseDescription} of the purchase.
     * @param amount {@code PurchaseMoneySpent} of the purchase.
     * @param date {@code LocalDate} of the purchase.
     */
    @JsonCreator
    public JsonAdaptedPurchase(@JsonProperty("description") String description, @JsonProperty("amount") String amount,
                               @JsonProperty("date") String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Converts a given {@code Purchase} into this class for Jackson use.
     *
     * @param purchase {@code Purchase} to be used to construct the {@code JsonAdaptedPurchase}.
     */
    public JsonAdaptedPurchase(Purchase purchase) {
        description = purchase.getDescription().purchaseDescription;
        amount = purchase.getMoneySpent().toString();
        date = purchase.getDateOfPurchase().format(Purchase.getDateFormat());
    }

    /**
     * Converts this Jackson-friendly adapted {@code Purchase} object into the model's {@code Purchase} object.
     *
     * @return {@code Purchase} of the Jackson-friendly adapted {@code Purchase}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Purchase}.
     */
    @Override
    public Purchase toModelType() throws IllegalValueException {
        boolean isValidDescription = description != null && PurchaseDescription.isValidDescription(description);
        boolean isValidAmount = amount != null && PurchaseMoneySpent.isValidAmount(amount);

        if (!isValidDescription || !isValidAmount) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTRIBUTES);
        }

        try {
            return new Purchase(
                    new PurchaseDescription(description),
                    new PurchaseMoneySpent(amount),
                    LocalDate.parse(date, Purchase.getDateFormat()));
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTRIBUTES);
        }
    }
}
