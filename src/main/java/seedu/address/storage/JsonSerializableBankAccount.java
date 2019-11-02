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
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;

/**
 * An Immutable BankAccount that is serializable to JSON format.
 */
@JsonRootName(value = "bankaccount")
class JsonSerializableBankAccount {

    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Transactions list contains duplicate transaction(s).";
    public static final String MESSAGE_DUPLICATE_BUDGET = "Budgets list contains duplicate budget(s).";
    public static final String MESSAGE_DUPLICATE_LEDGER = "Ledger list contains duplicate Ledger operation(s).";

    private final List<JsonAdaptedBankOperations> transactions = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();
    private final List<JsonAdaptedLedgerOperations> ledgers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBankAccount} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableBankAccount(@JsonProperty("transactions") List<JsonAdaptedBankOperations> transactions,
                                       @JsonProperty("budgets") List<JsonAdaptedBudget> budgets,
                                       @JsonProperty("ledgers") List<JsonAdaptedLedgerOperations> ledgers) {
        this.transactions.addAll(transactions);
        this.budgets.addAll(budgets);
        this.ledgers.addAll(ledgers);
    }

    /**
     * Converts a given {@code ReadOnlyBankAccount} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBankAccount}.
     */
    public JsonSerializableBankAccount(ReadOnlyUserState source) {
        budgets
            .addAll(source.getBankAccount().getBudgetHistory()
                .stream()
                .map(JsonAdaptedBudget::new)
                .collect(Collectors.toList()));
        transactions
            .addAll(source.getBankAccount().getTransactionHistory()
                .stream()
                .map(JsonAdaptedBankOperations::new)
                .collect(Collectors.toList()));
        ledgers
            .addAll(source.getLedger().getLedgerHistory()
                    .stream()
                    .map(JsonAdaptedLedgerOperations::new)
                    .collect(Collectors.toList()));
    }

    /**
     * Converts this bank account into the model's {@code BankAccount} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserState toModelType() throws IllegalValueException {
        UserState user = new UserState();
        for (JsonAdaptedBankOperations jsonAdaptedBankOperations : transactions) {
            BankAccountOperation txn = jsonAdaptedBankOperations.toModelType();
            if (user.hasTransaction(txn)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRANSACTION);
            }
            user.addOperation(txn);
        }

        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            if (user.hasBudget(budget)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUDGET);
            }
            user.addBudget(budget);
        }
        return user;
    }

}
