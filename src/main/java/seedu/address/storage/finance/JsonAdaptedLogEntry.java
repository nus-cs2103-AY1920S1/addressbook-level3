package seedu.address.storage.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.logentry.Amount;
import seedu.address.model.finance.logentry.Description;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.TransactionDate;

/**
 * Jackson-friendly version of {@link LogEntry}.
 */
class JsonAdaptedLogEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Log entry's %s field is missing!";

    private final String amount;
    private final String tDate;
    private final String desc;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedLogEntry(@JsonProperty("amount") String amount, @JsonProperty("transactionDate") String tDate,
                               @JsonProperty("description") String desc) {
        this.amount = amount;
        this.tDate = tDate;
        this.desc = desc;
    }

    /**
     * Converts a given {@code LogEntry} into this class for Jackson use.
     */
    public JsonAdaptedLogEntry(LogEntry source) {
        amount = source.getAmount().fullName;
        tDate = source.getTransactionDate().value;
        desc = source.getDescription().value;
    }

    /**
     * Converts this Jackson-friendly adapted log entry object into the model's {@code LogEntry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log entry.
     */
    public LogEntry toModelType() throws IllegalValueException {

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (tDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionDate.class.getSimpleName()));
        }
        if (!TransactionDate.isValidTransactionDate(tDate)) {
            throw new IllegalValueException(TransactionDate.MESSAGE_CONSTRAINTS);
        }
        final TransactionDate modelTransactionDate = new TransactionDate(tDate);

        if (desc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(desc)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(desc);

        return new LogEntry(modelAmount, modelTransactionDate, modelDescription);
    }

}
