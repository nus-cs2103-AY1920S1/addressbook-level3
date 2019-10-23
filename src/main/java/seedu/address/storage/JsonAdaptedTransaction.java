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
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.OutTransaction;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Date;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String amount;
    private final String date;
    private final List<JsonAdaptedCategory> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("amount") String amount, @JsonProperty("date") String date,
                                  @JsonProperty("tagged") List<JsonAdaptedCategory> tagged) {
        this.amount = amount;
        this.date = date;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Transaction} into this class for Jason use.
     */
    public JsonAdaptedTransaction(BankAccountOperation source) {
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

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(Double.parseDouble(amount))) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        final Set<Category> modelCategories = new HashSet<>(transactionCategories);

        if ((Double.parseDouble(amount)) < 0) {
            /* if amount is negative */
            return new OutTransaction(new Amount(Double.parseDouble(amount))
                    .makeNegative(), new Date(date), modelCategories);
        } else {
            /* if amount is positive */
            return new InTransaction(new Amount(Double.parseDouble(amount)), new Date(date), modelCategories);
        }
    }

}
