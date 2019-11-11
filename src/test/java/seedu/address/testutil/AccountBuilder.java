package seedu.address.testutil;

import seedu.address.model.account.Account;
import seedu.address.model.account.Username;

/**
 * A utility class to help with building Account objects.
 */
public class AccountBuilder {

    public static final String DEFAULT_USERNAME = "bryan_ng";
    public static final String DEFAULT_PASSWORD = "admin";

    private Username username;
    private String password;

    public AccountBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = DEFAULT_PASSWORD;
        // do i have to hash it here???????????????????????????
    }

    /**
     * Initializes the EarningsBuilder with the data of {@code accountToCopy}.
     */
    public AccountBuilder(Account accountToCopy) {
        username = accountToCopy.getUsername();
        password = accountToCopy.getPassword();
    }


    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Earnings} that we are building.
     */
    public AccountBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public Account build() {
        return new Account(username, password);
    }
}
