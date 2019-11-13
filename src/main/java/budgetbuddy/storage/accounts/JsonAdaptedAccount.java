package budgetbuddy.storage.accounts;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.TransactionList;
import budgetbuddy.storage.transactions.JsonAdaptedTransaction;


/**
 * Jackson-friendly version of {@link budgetbuddy.model.account.Account}.
 */
public class JsonAdaptedAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    private final String name;
    private final String description;
    private final String balance;
    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAccount} with the given account details.
     */
    public JsonAdaptedAccount(@JsonProperty("name") String name,
                           @JsonProperty("description") String description,
                           @JsonProperty("transactions") List<JsonAdaptedTransaction> transactionList,
                              @JsonProperty("balance") String balance) {
        requireAllNonNull(name, description, transactionList);
        this.name = name;
        this.description = description;
        this.transactions.addAll(transactionList);
        this.balance = balance;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedAccount(Account source) {
        name = source.getName().toString();
        description = source.getDescription().toString();
        transactions.addAll(source.getTransactionList().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedTransaction::new).collect(Collectors.toList()));
        balance = String.valueOf(source.getBalance());
    }

    /**
     * Converts this Jackson-friendly adapted account object into the model's {@code Account} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted account.
     */
    public Account toModelType() throws IllegalValueException {
        return new Account(getValidatedName(), getValidatedDescription(),
                getValidatedTransactionList(), getValidatedBalance());
    }

    /**
     * Checks that adapted name can be converted into model's {@code Name} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Name getValidatedName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    /**
     * Checks that adapted description can be converted into model's {@code Description} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Description getValidatedDescription() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(description);
    }

    /**
     * Checks that adapted transactionList can be converted into model's {@code transactionList} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private TransactionList getValidatedTransactionList() throws IllegalValueException {
        TransactionList transactionList = new TransactionList();
        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            transactionList.add(jsonAdaptedTransaction.toModelType());
        }
        return transactionList;
    }

    /**
     * Checks that adapted balance can be converted into model's {@code balance}
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private long getValidatedBalance() throws IllegalValueException {
        try {
            return Long.parseLong(balance);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Invalid balance", e);
        }
    }
}
