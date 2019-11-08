package budgetbuddy.testutil.accountutil;

import budgetbuddy.logic.commands.accountcommands.AccountEditCommand.AccountEditDescriptor;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;

/**
 * A utility class to help with building AccountEditDescriptor objects.
 */
public class AccountEditDescriptorBuilder {

    private AccountEditDescriptor accountEditDescriptor;

    public AccountEditDescriptorBuilder() {
        accountEditDescriptor = new AccountEditDescriptor();
    }

    /**
     * Returns a {@code AccountEditDescriptor} with fields containing {@code account}'s details.
     */
    public AccountEditDescriptorBuilder(Account account) {
        accountEditDescriptor = new AccountEditDescriptor();
        accountEditDescriptor.setName(account.getName());
        accountEditDescriptor.setDescription(account.getDescription());
    }

    /**
     * Sets the {@code Name} of the {@code NameEditDescriptor} that we are building.
     */
    public AccountEditDescriptorBuilder withName(String name) {
        accountEditDescriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code AccountEditDescriptor} that we are building.
     */
    public AccountEditDescriptorBuilder withDescription(String description) {
        accountEditDescriptor.setDescription(new Description(description));
        return this;
    }

    public AccountEditDescriptor build() {
        return accountEditDescriptor;
    }
}
