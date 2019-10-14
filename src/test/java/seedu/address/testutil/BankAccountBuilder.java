package seedu.address.testutil;

import seedu.address.model.BankAccount;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class to help with building BankAccount objects.
 * Example usage: <br>
 *     {@code BankAccount ba = new BankAccountBuilder().withPerson("John", "Doe").build();}
 */
public class BankAccountBuilder {

    private BankAccount bankAccount;

    public BankAccountBuilder() { bankAccount = new BankAccount(); }

    public BankAccountBuilder(BankAccount bankAccount) { this.bankAccount = bankAccount; }

    /**
     * Adds a new {@code Transaction} to the {@code BankAccount} that we are building.
     */
    public BankAccountBuilder withTransaction(Transaction transaction) {
        bankAccount.addTransaction(transaction);
        return this;
    }

    public BankAccount build() {
        return bankAccount;
    }
}
