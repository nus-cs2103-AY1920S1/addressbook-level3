package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Ledger;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.transaction.Split;
import seedu.address.model.util.Date;

/**
 * Jackson-friendly version of {@link JsonAdaptedLedgerOperations}.
 */
public class JsonAdaptedLedgerOperations {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ledger's %s field is missing!";

    private final String date;
    private final String amount;

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

        return new ReceiveMoney(modelDate, modelAmount);
    }
}
