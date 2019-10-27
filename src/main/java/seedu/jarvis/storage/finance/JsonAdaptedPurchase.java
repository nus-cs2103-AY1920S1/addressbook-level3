package seedu.jarvis.storage.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.finance.purchase.PurchaseDescription;
import seedu.jarvis.model.finance.purchase.PurchaseMoneySpent;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Jackson-friendly version of {@link Purchase}.
 */
public class JsonAdaptedPurchase {

    public static final String MESSAGE_INVALID_ATTRIBUTES = "Invalid purchase attributes.";

    private final String description;
    private final String moneySpent;
    private final String date;

    @JsonCreator
    public JsonAdaptedPurchase(@JsonProperty("description") String description,
                               @JsonProperty("moneySpent") String moneySpent, @JsonProperty("date") String date) {
        this.description = description;
        this.moneySpent = moneySpent;
        this.date = date;
    }

    public JsonAdaptedPurchase(Purchase purchase) {
        description = purchase.getDescription().purchaseDescription;
        moneySpent = purchase.getMoneySpent().toString();
        date = purchase.getDateOfPurchase().format(Purchase.getDateFormat());
    }

    public Purchase toModelType() throws IllegalValueException {
        boolean isValidDescription = description != null && PurchaseDescription.isValidDescription(description);
        boolean isValidMoneySpent = moneySpent != null && PurchaseMoneySpent.isValidAmount(moneySpent);

        if (!isValidDescription || !isValidMoneySpent) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTRIBUTES);
        }

        try {
            return new Purchase(
                    new PurchaseDescription(description),
                    new PurchaseMoneySpent(moneySpent),
                    LocalDate.parse(date, Purchase.getDateFormat()));
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTRIBUTES);
        }
    }
}
