package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.OutTransaction;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Date;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
class JsonAdaptedBankOperations {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String description;
    private final String amount;
    private final String date;
    private final List<JsonAdaptedCategory> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBankOperations} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedBankOperations(@JsonProperty("description") String description,
                                     @JsonProperty("amount") String amount, @JsonProperty("date") String date,
                                     @JsonProperty("tagged") List<JsonAdaptedCategory> tagged) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Transaction} into this class for Jason use.
     */
    public JsonAdaptedBankOperations(BankAccountOperation source) {
        description = source.getDescription().toString();
        amount = source.getAmount().toString();
        date = source.getDate().toString();
        tagged.addAll(source.getCategories().stream()
            .map(JsonAdaptedCategory::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public BankAccountOperation toModelType() throws IllegalValueException {
        final List<Category> transactionCategories = new ArrayList<>();
        for (JsonAdaptedCategory category : tagged) {
            transactionCategories.add(category.toModelType());
        }

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
        if (!Amount.isValidAmount(Double.parseDouble(amount))) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }

        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        final Date modelDate = new Date(date);

        final Set<Category> modelCategories = new HashSet<>(transactionCategories);

        if ((Double.parseDouble(amount)) < 0) {
            /* if amount is negative */
            return new OutTransaction(modelAmount.makeNegative(), modelDate, modelDescription, modelCategories);
        } else {
            /* if amount is positive */
            return new InTransaction(modelAmount, modelDate, modelDescription, modelCategories);
        }
    }

}
