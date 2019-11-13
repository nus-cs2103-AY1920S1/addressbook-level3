package budgetbuddy.testutil.accountutil;

import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.TransactionList;

/**
 * A utility class to help with building Account objects.
 */
public class AccountBuilder {

    public static final Name DEFAULT_NAME = new Name("John");
    public static final Description DEFAULT_DESCRIPTION = new Description("The description.");
    public static final TransactionList DEFAULT_TRANSACTIONLIST = new TransactionList();
    public static final long DEFAULT_BALANCE = 0;

    private Name name;
    private Description description;
    private TransactionList transactionList;
    private long balance;

    public AccountBuilder() {
        this.name = DEFAULT_NAME;
        this.description = DEFAULT_DESCRIPTION;
        this.transactionList = DEFAULT_TRANSACTIONLIST;
        this.balance = DEFAULT_BALANCE;
    }

    public AccountBuilder(Account toCopy) {
        this.name = toCopy.getName();
        this.description = toCopy.getDescription();
        this.transactionList = toCopy.getTransactionList();
        this.balance = toCopy.getBalance();
    }

    /**
     * Sets the {@code Name} of the {@code Name} that we are building.
     */
    public AccountBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Account} that we are building.
     */
    public AccountBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Account build() {
        return new Account(name, description, transactionList, 0);
    }
}
