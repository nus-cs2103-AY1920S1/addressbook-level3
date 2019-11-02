package seedu.address.testutil;

import seedu.address.model.UserState;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * A utility class to help with building BankAccount objects.
 * Example usage: <br>
 *     {@code BankAccount ba = new UserStateBuilder().withPerson("John", "Doe").build();}
 */
public class UserStateBuilder {

    private UserState userState;

    public UserStateBuilder() {
        userState = new UserState();
    }

    public UserStateBuilder(UserState bankAccount) {
        this.userState = bankAccount;
    }

    /**
     * Adds a new {@code BankAccountOperation} to the {@code BankAccount} that we are building.
     * @param transaction An operation
     */
    public UserStateBuilder withTransaction(BankAccountOperation transaction) {
        userState.addOperation(transaction);
        return this;
    }

    public UserState build() {
        return userState;
    }
}
