package seedu.savenus.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.wallet.CurrentBalance;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.Wallet;

/**
 * Jackson-friendly version of {@link Wallet}.
 */
class JsonAdaptedWallet {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wallet's %s field is missing!";

    // Properties
    private final String currentBalance;
    private final String daysToExpire;


    /**
     * Constructs a {@code JsonAdaptedFood} with the given wallet details.
     */
    @JsonCreator
    public JsonAdaptedWallet(@JsonProperty("currentBalance") String currentBalance,
                             @JsonProperty("daysToExpire") String daysToExpire) {
        this.currentBalance = currentBalance;
        this.daysToExpire = daysToExpire;
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedWallet(Wallet source) {
        currentBalance = String.format("%.02f", source.getCurrentBalance());
        daysToExpire = String.format("%.02f", source.getDaysToExpire());
    }

    /**
     * Converts this Jackson-friendly adapted food object into the model's {@code Food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food.
     */
    public Wallet toModelType() throws IllegalValueException {
        if (currentBalance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CurrentBalance.class.getSimpleName()));
        }
        if (!CurrentBalance.isValidCurrentBalance(currentBalance)) {
            throw new IllegalValueException(CurrentBalance.MESSAGE_CONSTRAINTS);
        }

        final CurrentBalance modelCurrentBalance = new CurrentBalance(currentBalance);


        if (daysToExpire == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DaysToExpire.class.getSimpleName()));
        }
        if (!DaysToExpire.isValidDaysToExpire(daysToExpire)) {
            throw new IllegalValueException(DaysToExpire.MESSAGE_CONSTRAINTS);
        }

        final DaysToExpire modelDaysToExpire = new DaysToExpire(daysToExpire);

        return new Wallet(modelCurrentBalance, modelDaysToExpire);
    }
}
