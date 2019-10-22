package seedu.address.testutil;

import seedu.address.model.BankAccount;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * A utility class to help with building BankAccount objects.
 * Example usage: <br>
 *     {@code BankAccount ba = new BankAccountBuilder().withPerson("John", "Doe").build();}
 */
public class BankAccountBuilder {

    private BankAccount bankAccount;

    public BankAccountBuilder() {
        bankAccount = new BankAccount();
    }

    public BankAccountBuilder(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * Adds a new {@code BankAccountOperation} to the {@code BankAccount} that we are building.
     * @param transaction An operation
     */
    public BankAccountBuilder withTransaction(BankAccountOperation transaction) {
        bankAccount.addTransaction(transaction);
        return this;
    }

    public BankAccount build() {
        return bankAccount;
    }
}
