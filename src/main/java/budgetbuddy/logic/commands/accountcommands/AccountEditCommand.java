package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.commons.util.CollectionUtil;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.exceptions.AccountNotFoundException;
import budgetbuddy.model.account.exceptions.DuplicateAccountException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;

/**
 * Edits an account
 */
public class AccountEditCommand extends Command {

    public static final String COMMAND_WORD = "account edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an account.\n"
            + "Parameters: "
            + "<account number> "
            + String.format("[%sNAME]", PREFIX_NAME) + " "
            + String.format("[%sDESCRIPTION]", PREFIX_DESCRIPTION) + " "
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_NAME + "food";

    public static final String MESSAGE_SUCCESS = "Account edited: %1$s";
    public static final String MESSAGE_UNEDITED = "At least one field must be provided for editing.";
    public static final String MESSAGE_FAILURE = "The account targeted for editing could not be found.";

    private final Index targetAccountIndex;
    private final AccountEditDescriptor accountEditDescriptor;

    public AccountEditCommand(Index targetAccountIndex, AccountEditDescriptor accountEditDescriptor) {
        requireAllNonNull(targetAccountIndex, accountEditDescriptor);
        this.targetAccountIndex = targetAccountIndex;
        this.accountEditDescriptor = new AccountEditDescriptor(accountEditDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getAccountsManager());

        AccountsManager accountsManager = model.getAccountsManager();

        Account editedAccount;
        try {
            Account targetAccount = accountsManager.getAccount(targetAccountIndex);
            editedAccount = createEditedAccount(targetAccount, accountEditDescriptor);
            accountsManager.editAccount(targetAccountIndex, editedAccount);
        } catch (AccountNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE);
        } catch (DuplicateAccountException e) {
            throw new CommandException(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_INDEX);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedAccount), CommandCategory.ACCOUNT);
    }

    /**
     * Creates and returns a {@code Account} with the details of {@code accountToEdit},
     * edited with {@code accountEditDescriptor}.
     */
    private static Account createEditedAccount(Account accountToEdit, AccountEditDescriptor accountEditDescriptor) {
        assert accountToEdit != null;

        Name updatedName = accountEditDescriptor.getName().orElse(accountToEdit.getName());
        Description updatedDescription = accountEditDescriptor.getDescription().orElse(accountToEdit.getDescription());

        return new Account(updatedName, updatedDescription,
                accountToEdit.getTransactionList(), accountToEdit.getBalance());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AccountEditCommand)) {
            return false;
        }

        AccountEditCommand otherCommand = (AccountEditCommand) other;
        return targetAccountIndex.equals(otherCommand.targetAccountIndex)
                && accountEditDescriptor.equals(otherCommand.accountEditDescriptor);
    }

    /**
     * Stores the details to edit the loan with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class AccountEditDescriptor {
        private Name name;
        private Description description;

        public AccountEditDescriptor() {}

        public AccountEditDescriptor(AccountEditDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if any field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof AccountEditDescriptor)) {
                return false;
            }

            AccountEditDescriptor e = (AccountEditDescriptor) other;
            return getName().equals(e.getName())
                    && getDescription().equals(e.getDescription());
        }
    }
}

