package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.accountcommands.AccountEditCommand.AccountEditDescriptor;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.account.Account;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.accountutil.AccountBuilder;
import budgetbuddy.testutil.accountutil.AccountEditDescriptorBuilder;
import budgetbuddy.testutil.accountutil.TypicalAccounts;

public class AccountEditCommandTest {

    private Model model = new ModelManager();

    @BeforeEach
    public void initialize() {
        model.getAccountsManager().addAccount(TypicalAccounts.JAPAN_TRIP);
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AccountEditCommand(null, null));
        assertThrows(NullPointerException.class, () ->
                new AccountEditCommand(null, new AccountEditDescriptor()));
        assertThrows(NullPointerException.class, () ->
                new AccountEditCommand(TypicalIndexes.INDEX_FIRST_ITEM, null));
    }

    @Test
    public void execute_allFieldsSpecified_editSuccess() {
        Account editedAccount = new AccountBuilder().build();
        AccountEditDescriptor accountEditDescriptor = new AccountEditDescriptorBuilder(editedAccount).build();
        AccountEditCommand accountEditCommand = new AccountEditCommand(
                TypicalIndexes.INDEX_SECOND_ITEM, accountEditDescriptor);

        String expectedMessage = String.format(
                "Account edited: John (The description.)");
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.ACCOUNT);
        Model expectedModel = new ModelManager();
        expectedModel.getAccountsManager().addAccount(editedAccount);

        assertCommandSuccess(accountEditCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_editSuccess() {
        Account editedAccount = new AccountBuilder(TypicalAccounts.FOOD).withDescription("New Description").build();
        AccountEditDescriptor accountEditDescriptor = new AccountEditDescriptorBuilder(editedAccount).build();
        AccountEditCommand accountEditCommand = new AccountEditCommand(
                TypicalIndexes.INDEX_SECOND_ITEM, accountEditDescriptor);

        String expectedMessage = String.format(
                "Account edited: food (New Description)");
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.ACCOUNT);
        Model expectedModel = new ModelManager();
        expectedModel.getAccountsManager().addAccount(new AccountBuilder(editedAccount).build());

        assertCommandSuccess(accountEditCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldsSpecified_throwsCommandException() {
        AccountEditDescriptor accountEditDescriptor = new AccountEditDescriptorBuilder().build();
        AccountEditCommand accountEditCommand = new AccountEditCommand(
                TypicalIndexes.INDEX_FIRST_ITEM, accountEditDescriptor);

        assertThrows(CommandException.class, () -> accountEditCommand.execute(model));
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() {
        Account editedAccount = new AccountBuilder().build();
        AccountEditDescriptor accountEditDescriptor = new AccountEditDescriptorBuilder(editedAccount).build();
        AccountEditCommand accountEditCommand = new AccountEditCommand(
                TypicalIndexes.INDEX_THIRD_ITEM, accountEditDescriptor);

        assertThrows(CommandException.class, () -> accountEditCommand.execute(model));
    }

    @Test
    public void equals() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        AccountEditDescriptor accountEditDescriptor = new AccountEditDescriptorBuilder(TypicalAccounts.FOOD).build();
        AccountEditCommand accountEditCommand = new AccountEditCommand(targetIndex, accountEditDescriptor);

        // same object -> returns true
        assertEquals(accountEditCommand, accountEditCommand);

        // same values -> returns true
        AccountEditCommand accountEditCommandCopy = new AccountEditCommand(targetIndex, accountEditDescriptor);
        assertEquals(accountEditCommand, accountEditCommandCopy);

        // different types -> returns false
        assertNotEquals(accountEditCommand, 5);

        // null -> returns false
        assertNotEquals(accountEditCommand, null);

        // different index -> returns false
        AccountEditCommand accountEditCommandWithDifferentIndex =
                new AccountEditCommand(TypicalIndexes.INDEX_SECOND_ITEM, accountEditDescriptor);
        assertNotEquals(accountEditCommand, accountEditCommandWithDifferentIndex);

        // different descriptor -> returns false
        AccountEditCommand accountEditCommandWithDifferentDescriptor =
                new AccountEditCommand(targetIndex,
                        new AccountEditDescriptorBuilder(TypicalAccounts.SCHOOL_FEES).build());
        assertNotEquals(accountEditCommand, accountEditCommandWithDifferentDescriptor);
    }
}
