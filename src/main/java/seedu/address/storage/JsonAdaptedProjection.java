package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.person.Name;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.UniqueBudgetList;
import seedu.address.model.transaction.UniqueTransactionList;
import seedu.address.model.util.Date;

/**
 * Jackson-friendly version of {@link Projection}.
 */
class JsonAdaptedProjection {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Projection's %s field is missing!";

    private final List<JsonAdaptedBankOperations> transactions = new ArrayList<>();
    private final String amount;
    private final String date;
    private List<JsonAdaptedBudget> budgets = new ArrayList<>();
    private JsonAdaptedCategory category;

    /**
     * Constructs a {@code JsonAdaptedProjection} with the given projection details.
     */
    @JsonCreator
    public JsonAdaptedProjection(@JsonProperty("transactions") List<JsonAdaptedBankOperations> transactions,
                                 @JsonProperty("amount") String amount, @JsonProperty("date") String date,
                                 @JsonProperty("budgets") List<JsonAdaptedBudget> budgets,
                                 @JsonProperty("category") JsonAdaptedCategory category) {
        this.transactions.addAll(transactions);
        this.amount = amount;
        this.date = date;
        this.budgets.addAll(budgets);
        this.category = category;
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
        this.budgets.addAll(source.getBudgets().stream()
                .map(JsonAdaptedBudget::new).collect(Collectors.toList()));
        this.category = new JsonAdaptedCategory(source.getCategory());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Projection} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted projections.
     */
    public Projection toModelType() throws IllegalValueException {

        if (this.transactions.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Transaction.class.getSimpleName()));
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

        if (category == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName()));
        }

        if (this.budgets == null || this.budgets.isEmpty()) {
            return new Projection(txns.asUnmodifiableObservableList(),
                    new Amount(Double.parseDouble(amount)), new Date(date), category.toModelType());
        }
        UniqueBudgetList bgts = new UniqueBudgetList();
        for (JsonAdaptedBudget budget : this.budgets) {
            bgts.add(budget.toModelType());
        }

        return new Projection(txns.asUnmodifiableObservableList(), new Amount(Double.parseDouble(amount)),
                new Date(date), bgts.asUnmodifiableObservableList(), this.category.toModelType());
    }

}
