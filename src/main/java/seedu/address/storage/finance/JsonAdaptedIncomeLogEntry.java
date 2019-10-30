package seedu.address.storage.finance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.IncomeLogEntry;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Jackson-friendly version of {@link IncomeLogEntry}.
 */
class JsonAdaptedIncomeLogEntry extends JsonAdaptedLogEntry {

    private final String logEntryType;
    private final String from;

    /**
     * Constructs a {@code JsonAdaptedIncomeLogEntry} with the given log entry details.
     */
    @JsonCreator
    public JsonAdaptedIncomeLogEntry(@JsonProperty("amount") String amount,
                                     @JsonProperty("transactionDate") String tDate,
                                     @JsonProperty("description") String desc,
                                     @JsonProperty("transactionMethod") String tMethod,
                                     @JsonProperty("categories") List<JsonAdaptedCategory> categories,
                                     @JsonProperty("logEntryType") String logEntryType,
                                     @JsonProperty("from") String from) {
        super(amount, tDate, desc, tMethod, categories);
        this.logEntryType = logEntryType;
        this.from = from;
    }

    /**
     * Converts a given {@code IncomeLogEntry} into this class for Jackson use.
     */
    public JsonAdaptedIncomeLogEntry(IncomeLogEntry source) {
        super(source);
        logEntryType = source.getLogEntryType();
        from = source.getFrom().name;
    }

    /**
     * Converts this Jackson-friendly adapted log entry object into the model's {@code IncomeLogEntry} object.
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

        if (tMethod == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, TransactionMethod.class.getSimpleName()));
        }
        if (!TransactionMethod.isValidTransactionMet(tMethod)) {
            throw new IllegalValueException(TransactionMethod.MESSAGE_CONSTRAINTS);
        }
        final TransactionMethod modelTransactionMethod = new TransactionMethod(tMethod);

        final List<Category> logEntryCategories = new ArrayList<>();
        for (JsonAdaptedCategory cat : categories) {
            logEntryCategories.add(cat.toModelType());
        }
        final Set<Category> modelLogEntryCategories = new HashSet<>(logEntryCategories);

        assert logEntryType != null;

        if (from == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Person.class.getSimpleName()));
        }
        if (!Person.isValidName(from)) {
            throw new IllegalValueException(Person.MESSAGE_CONSTRAINTS);
        }
        final Person modelPerson = new Person(from);

        return new IncomeLogEntry(modelAmount, modelTransactionDate, modelDescription,
                modelTransactionMethod, modelLogEntryCategories, modelPerson);
    }

}
