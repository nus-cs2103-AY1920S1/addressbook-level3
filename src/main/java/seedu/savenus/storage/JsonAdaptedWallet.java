package seedu.savenus.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.savenus.commons.exceptions.IllegalValueException;

import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;

/**
 * Jackson-friendly version of {@link Wallet}.
 */
class JsonAdaptedWallet {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wallet's %s field is missing!";

    private final String remainingBudget;
    private final String daysToExpire;

    /**
     * Constructs a {@code JsonAdaptedWallet} with the given wallet details.
     */
    @JsonCreator
    public JsonAdaptedWallet(@JsonProperty("remainingBudget") String remainingBudget,
                             @JsonProperty("daysToExpire") String daysToExpire) {
        this.remainingBudget = remainingBudget;
        this.daysToExpire = daysToExpire;
        System.out.println("BUDGET: " + remainingBudget);
        System.out.println("DAYS: " + daysToExpire);

    }

    /**
     * Converts a given {@code Wallet} into this class for Jackson use.
     */
    public JsonAdaptedWallet(Wallet source) {
        remainingBudget = String.format("%.02f", source.getRemainingBudgetAmount());
        daysToExpire = String.format("%d", source.getNumberOfDaysToExpire());
    }

    /**
     * Converts this Jackson-friendly adapted wallet object into the model's {@code Wallet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wallet.
     */
    public Wallet toModelType() throws IllegalValueException {

        // Sanitisation for remainingBudget
        if (remainingBudget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RemainingBudget.class.getSimpleName()));
        }
        if (!RemainingBudget.isValidRemainingBudget(remainingBudget)) {
            throw new IllegalValueException(RemainingBudget.MESSAGE_CONSTRAINTS);
        }

        if (new RemainingBudget(remainingBudget).isOutOfBounds()) {
            throw new IllegalValueException(RemainingBudget.FLOATING_POINT_CONSTRAINTS);
        }

        final RemainingBudget modelRemainingBudget = new RemainingBudget(remainingBudget);

        // Sanitisation for daysToExpire
        if (daysToExpire == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DaysToExpire.class.getSimpleName()));
        }
        if (!DaysToExpire.isValidDaysToExpire(daysToExpire)) {
            throw new IllegalValueException(DaysToExpire.MESSAGE_CONSTRAINTS);
        }

        if (new DaysToExpire(daysToExpire).isOutOfBounds()) {
            throw new IllegalValueException(DaysToExpire.INTEGER_CONSTRAINTS);
        }

        final DaysToExpire modelDaysToExpire = new DaysToExpire(daysToExpire);

        return new Wallet(modelRemainingBudget, modelDaysToExpire);
    }
}
