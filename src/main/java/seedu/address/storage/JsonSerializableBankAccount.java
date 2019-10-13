package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BankAccount;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.person.Person;

/**
 * An Immutable BankAccount that is serializable to JSON format.
 */
@JsonRootName(value = "bankaccount")
class JsonSerializableBankAccount {

    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Transactions list contains duplicate transaction(s).";

    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBankAccount} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableBankAccount(@JsonProperty("persons") List<JsonAdaptedTransaction> transactions) {
        this.transactions.addAll(transactions);
    }

    /**
     * Converts a given {@code ReadOnlyBankAccount} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBankAccount}.
     */
    public JsonSerializableBankAccount(ReadOnlyBankAccount source) {
        transactions.addAll(source.getTransactionHistory().stream().map(JsonAdaptedTransaction::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BankAccount toModelType() throws IllegalValueException {
        BankAccount bankAccount = new BankAccount();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (bankAccount.hasTransaction(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRANSACTION);
            }
            bankAccount.addTransaction(person);
        }
        return bankAccount;
    }

}
