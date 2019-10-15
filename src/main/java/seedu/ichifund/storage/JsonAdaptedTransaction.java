package seedu.ichifund.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.Transaction;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
public class JsonAdaptedTransaction {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String description;
    private final String amount;
    private final String category;
    private final JsonAdaptedDate date;
    private final String transactionType;

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("description") String description,
                                  @JsonProperty("amount") String amount, @JsonProperty("category") String category,
                                  @JsonProperty("date") JsonAdaptedDate date,
                                  @JsonProperty("transactionType") String transactionType) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.transactionType = transactionType;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        this.description = source.getDescription().toString();
        this.amount = source.getAmount().toString();
        this.category = source.getCategory().toString();
        this.date = new JsonAdaptedDate(source.getDate());
        this.transactionType = source.getTransactionType().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public Transaction toModelType() throws IllegalValueException {
        final Date modelDate = date.toModelType();

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        final Category modelCategory = new Category(category);

        if (transactionType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionType.class.getSimpleName()));
        }
        if (!TransactionType.isValidTransactionType(transactionType)) {
            throw new IllegalValueException(TransactionType.MESSAGE_CONSTRAINTS);
        }
        final TransactionType modelTransactionType = new TransactionType(transactionType);

        return new Transaction(modelDescription, modelAmount, modelCategory, modelDate, modelTransactionType);
    }
}
