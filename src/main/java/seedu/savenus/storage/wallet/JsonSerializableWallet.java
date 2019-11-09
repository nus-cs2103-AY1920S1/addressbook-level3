package seedu.savenus.storage.wallet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.wallet.Wallet;

/**
 * An Immutable Wallet that is serializable to JSON format.
 */
@JsonRootName(value = "wallet")
public class JsonSerializableWallet {

    private JsonAdaptedWallet wallet;

    /**
     * Construct a {@code JsonSerializableWallet} with the given savingsHistory.
     */
    @JsonCreator
    public JsonSerializableWallet(@JsonProperty("wallet") JsonAdaptedWallet wallet) {
        this.wallet = wallet;
    }

    /**
     * Converts a given {@code Wallet} into this class for Jackson use.
     *
     * @param wallet future changes will not affect the created {@code JsonSerializableWallet}.
     */
    public JsonSerializableWallet(Wallet wallet) {
        this.wallet = new JsonAdaptedWallet(wallet);
    }

    /**
     * Converts this wallet into the model's {@code Wallet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Wallet toModelType() throws IllegalValueException {
        return this.wallet.toModelType();
    }
}
