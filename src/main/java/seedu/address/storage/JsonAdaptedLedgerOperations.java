package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.util.Date;

/**
 * Jackson-friendly version of {@link JsonAdaptedLedgerOperations}.
 */
public class JsonAdaptedLedgerOperations {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ledger's %s field is missing!";

    private final String date;
    private final String amount;

    // TODO: include peopleInvolved
    @JsonCreator
    public JsonAdaptedLedgerOperations(@JsonProperty("date") String date,
                                       @JsonProperty("amount") String amount) {
        this.date = date;
        this.amount = amount;
    }

    public JsonAdaptedLedgerOperations(LedgerOperation source) {
        date = source.getDate().toString();
        amount = source.getAmount().toString();
    }

    /**
     * Converts this Jackson-friendly adapted ledger object into the model's {@code Ledger} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ledger.
     */
    public LedgerOperation toModelType() throws IllegalValueException {

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        final Date modelDate = new Date(date);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(Double.parseDouble(amount))) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }

        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        // TODO
        return new ReceiveMoney(modelDate, modelAmount);
    }
}
