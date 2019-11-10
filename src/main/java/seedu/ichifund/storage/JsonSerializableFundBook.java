package seedu.ichifund.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Transaction;

/**
 * An Immutable FundBook that is serializable to JSON format.
 */
@JsonRootName(value = "fundbook")
class JsonSerializableFundBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Repeater's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_REPEATER = "Repeaters list contains duplicate repeater(s).";
    public static final String MESSAGE_DUPLICATE_BUDGET = "Budgets list contains duplicate budget(s).";
    public static final String MESSAGE_DUPLICATE_LOAN = "Budgets list contains duplicate budget(s).";

    private final String currentRepeaterUniqueId;
    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();
    private final List<JsonAdaptedRepeater> repeaters = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFundBook} with the given person and budget.
     */
    @JsonCreator
    public JsonSerializableFundBook(
            @JsonProperty("currentRepeaterUniqueId") String currentRepeaterUniqueId,
            @JsonProperty("transactions") List<JsonAdaptedTransaction> transactions,
            @JsonProperty("repeaters") List<JsonAdaptedRepeater> repeaters,
            @JsonProperty("loans") List<JsonAdaptedLoan> loans,
            @JsonProperty("budgets") List<JsonAdaptedBudget> budgets) {
        this.currentRepeaterUniqueId = currentRepeaterUniqueId;
        this.transactions.addAll(transactions);
        this.repeaters.addAll(repeaters);
        this.budgets.addAll(budgets);
    }

    /**
     * Converts a given {@code ReadOnlyFundBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFundBook}.
     */
    public JsonSerializableFundBook(ReadOnlyFundBook source) {
        currentRepeaterUniqueId = source.getCurrentRepeaterUniqueId().toString();
        transactions.addAll(source.getTransactionList().stream().map(JsonAdaptedTransaction::new)
                .collect(Collectors.toList()));
        repeaters.addAll(source.getRepeaterList().stream().map(JsonAdaptedRepeater::new).collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
    }

    /**
     * Converts this fund book into the model's {@code FundBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FundBook toModelType() throws IllegalValueException {
        FundBook fundBook = new FundBook();

        if (currentRepeaterUniqueId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RepeaterUniqueId.class.getSimpleName()));
        }
        if (!RepeaterUniqueId.isValidRepeaterUniqueId(currentRepeaterUniqueId)) {
            throw new IllegalValueException(RepeaterUniqueId.MESSAGE_CONSTRAINTS);
        }
        final RepeaterUniqueId modelCurrentRepeaterUniqueId = new RepeaterUniqueId(currentRepeaterUniqueId);
        fundBook.setCurrentRepeaterUniqueId(modelCurrentRepeaterUniqueId);

        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            fundBook.addTransaction(transaction);
        }

        for (JsonAdaptedRepeater jsonAdaptedRepeater : repeaters) {
            Repeater repeater = jsonAdaptedRepeater.toModelType();
            if (fundBook.hasRepeater(repeater)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REPEATER);
            }
            fundBook.addRepeater(repeater);
        }

        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            if (fundBook.hasBudget(budget)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUDGET);
            }
            fundBook.addBudget(budget);
        }

        return fundBook;
    }

}
