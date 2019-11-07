package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.UserState;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

/**
 * An Immutable UserState that is serializable to JSON format.
 */
@JsonRootName(value = "userstate")
class JsonSerializableUserState {

    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Transactions list contains duplicate transaction(s).";
    public static final String MESSAGE_DUPLICATE_LEDGER = "Ledger list contains duplicate ledger(s).";
    public static final String MESSAGE_DUPLICATE_BUDGET = "Budgets list contains duplicate budget(s).";
    public static final String MESSAGE_DUPLICATE_PROJECTION = "Projections list contains duplicate projection(s).";

    private final List<JsonAdaptedBankOperations> transactions = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();
    private final List<JsonAdaptedLedgerOperations> ledgers = new ArrayList<>();
    private final List<JsonAdaptedProjection> projections = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUserState} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableUserState(@JsonProperty("transactions") List<JsonAdaptedBankOperations> transactions,
                                     @JsonProperty("budgets") List<JsonAdaptedBudget> budgets,
                                     @JsonProperty("ledgers") List<JsonAdaptedLedgerOperations> ledgers,
                                     @JsonProperty("projections") List<JsonAdaptedProjection> projections) {
        this.transactions.addAll(transactions);
        this.budgets.addAll(budgets);
        this.ledgers.addAll(ledgers);
        this.projections.addAll(projections);
    }

    public JsonSerializableUserState(ReadOnlyUserState source) {
        transactions
            .addAll(source.getBankAccount().getTransactionHistory()
                .stream()
                .map(JsonAdaptedBankOperations::new)
                .collect(Collectors.toList()));
        budgets
            .addAll(source.getBankAccount().getBudgetHistory()
                .stream()
                .map(JsonAdaptedBudget::new)
                .collect(Collectors.toList()));
        ledgers
                .addAll(source.getLedger().getLedgerHistory()
                .stream()
                .map(JsonAdaptedLedgerOperations::new)
                .collect(Collectors.toList()));
        projections
                .addAll(source.getBankAccount().getProjectionHistory()
                .stream()
                .map(JsonAdaptedProjection::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this user state into the model's {@code UserState} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserState toModelType() throws IllegalValueException {
        UserState userState = new UserState();
        for (JsonAdaptedBankOperations jsonAdaptedBankOperations : transactions) {
            BankAccountOperation txn = jsonAdaptedBankOperations.toModelType();
            userState.add(txn);
        }

        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            if (userState.has(budget)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUDGET);
            }
            userState.add(budget);
        }

        for (JsonAdaptedLedgerOperations jsonAdaptedLedgerOperations : ledgers) {
            LedgerOperation ledgerOperation = jsonAdaptedLedgerOperations.toModelType();
            if (userState.has(ledgerOperation)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LEDGER);
            }
            userState.add(ledgerOperation);
        }

        for (JsonAdaptedProjection jsonAdaptedProjection : projections) {
            Projection projection = jsonAdaptedProjection.toModelType();
            if (userState.has(projection)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECTION);
            }
            userState.add(projection);
        }

        return userState;
    }
}
