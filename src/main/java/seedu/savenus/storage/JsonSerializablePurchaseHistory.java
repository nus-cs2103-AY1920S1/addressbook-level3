package seedu.savenus.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.model.ReadOnlyPurchaseHistory;
import seedu.savenus.model.purchase.Purchase;

/**
 * An Immutable Purchase History that is serializable to JSON format.
 */
@JsonRootName(value = "savenus-purchases")

public class JsonSerializablePurchaseHistory {

    private final List<JsonAdaptedPurchase> purchases = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePurchaseHistory} with the given recommendations.
     */
    @JsonCreator
    public JsonSerializablePurchaseHistory(@JsonProperty("purchases") List<JsonAdaptedPurchase> purchases) {
        this.purchases.addAll(purchases);
    }

    /**
     * Converts a given {@code ReadOnlyPurchaseHistory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePurchaseHistory}.
     */
    public JsonSerializablePurchaseHistory(ReadOnlyPurchaseHistory source) {
        purchases.addAll(source.getPurchaseHistoryList().stream()
                .map(JsonAdaptedPurchase::new).collect(Collectors.toList()));
    }

    /**
     * Converts this PurchaseHistory into the model's {@code PurchaseHistory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PurchaseHistory toModelType() throws IllegalValueException {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        for (JsonAdaptedPurchase jsonAdaptedPurchase : purchases) {
            Purchase purchase = jsonAdaptedPurchase.toModelType();
            purchaseHistory.addPurchase(purchase);
        }
        return purchaseHistory;
    }

}

