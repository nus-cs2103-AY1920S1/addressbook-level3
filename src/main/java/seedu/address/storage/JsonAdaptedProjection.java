package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueTransactionList;
import seedu.address.model.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Projection}.
 */
class JsonAdaptedProjection {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Projection's %s field is missing!";

    private final List<JsonAdaptedBankOperations> transactions = new ArrayList<>();
    private final String amount;
    private final String date;
    private JsonAdaptedBudget budget;

    /**
     * Constructs a {@code JsonAdaptedProjection} with the given projection details.
     */
    @JsonCreator
    public JsonAdaptedProjection(@JsonProperty("transactions") List<JsonAdaptedBankOperations> transactions,
                                 @JsonProperty("amount") String amount, @JsonProperty("date") String date,
                                 @JsonProperty("budget") JsonAdaptedBudget budget) {
        this.transactions.addAll(transactions);
        this.amount = amount;
        this.date = date;
        this.budget = budget;
    }

    /**
     * Converts a given {@code Projection} into this class for Json use.
     */
    JsonAdaptedProjection(Projection source) {
        transactions.addAll(source.getTransactionHistory().stream()
                .map(JsonAdaptedBankOperations::new)
                .collect(Collectors.toList()));
        amount = source.getProjection().toString();
        date = source.getDate().toString();
        if (source.getBudget().isPresent()) {
            budget = new JsonAdaptedBudget(source.getBudget().get());
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Projection} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted projections.
     */
    public Projection toModelType() throws IllegalValueException {

        if(this.transactions.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Transaction.class.getSimpleName()));
        }

        UniqueTransactionList txns = new UniqueTransactionList();
        for (JsonAdaptedBankOperations bankAccountOperation : this.transactions) {
            txns.add(bankAccountOperation.toModelType());
        }
        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(Double.parseDouble(amount))) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (this.budget == null) {
            return new Projection(txns.asUnmodifiableObservableList(), new Amount(Double.parseDouble(amount)), new Date(date));
        }

        return new Projection(txns.asUnmodifiableObservableList(), new Amount(Double.parseDouble(amount)), new Date(date), this.budget.toModelType());
    }

}
