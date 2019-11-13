package budgetbuddy.model;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.exceptions.AccountNotFoundException;
import budgetbuddy.model.account.exceptions.DuplicateAccountException;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.accountutil.AccountBuilder;
import budgetbuddy.testutil.accountutil.TypicalAccounts;


public class AccountManagerTest {

    private AccountsManager accountsManager = new AccountsManager(TypicalAccounts.ACCOUNT_LIST, Index.fromOneBased(1));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AccountsManager(null, null));
    }

    @Test
    public void getAccount_indexBeyondListSize_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> accountsManager.getAccount(
                Index.fromZeroBased(accountsManager.size() + 1)));
    }

    @Test
    public void addAccount_duplicateAccount_throwsDuplicateAccountException() {
        assertThrows(DuplicateAccountException.class, () -> accountsManager.addAccount(TypicalAccounts.FOOD));
    }

    @Test
    public void editAccount_validIndexValidAccount_editedAccountReplacesTargetAccount() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        Account targetAccount = accountsManager.getAccount(targetIndex);
        Account editedAccount = new AccountBuilder(targetAccount).withName("Savings").build();

        accountsManager.editAccount(targetIndex, editedAccount);
        assertEquals(editedAccount, accountsManager.getAccount(targetIndex));
    }

    @Test
    public void editAccount_duplicateAccount_throwsDuplicateAccountException() {
        assertThrows(DuplicateAccountException.class, () -> accountsManager.editAccount(
                TypicalIndexes.INDEX_THIRD_ITEM, TypicalAccounts.FOOD));
    }

    @Test
    public void deleteAccount_indexBeyondListSize_throwsAccountNotFoundException() {
        assertThrows(AccountNotFoundException.class, () -> accountsManager.deleteAccount(
                Index.fromOneBased(accountsManager.size() + 1)));
    }

    @Test
    public void deleteAccount_validIndex_accountDeletedFromList() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;

        Account targetAccount = accountsManager.getAccount(targetIndex);

        accountsManager.deleteAccount(targetIndex);
        assertTrue(accountsManager.getAccounts().stream().noneMatch(account -> account.equals(targetAccount)));
    }

    @Test
    public void size() {
        assertEquals(4, accountsManager.size());
    }

    @Test
    public void updateFilteredAccountList_validPredicate_accountFoundFromList() {
        accountsManager.updateFilteredAccountList(s -> s.getName().toString().contains("food"));
        assertEquals(TypicalAccounts.FOOD, accountsManager.getFilteredAccountList().get(0));
        assertEquals(1, accountsManager.getFilteredAccountList().size());
    }

    @Test
    public void equals() {
        // same values -> returns true
        AccountsManager accountsManagerCopy = new AccountsManager(accountsManager.getAccounts(), Index.fromOneBased(1));
        assertEquals(accountsManagerCopy, accountsManager);

        // same object -> returns true
        assertEquals(accountsManager, accountsManager);

        // null -> returns false
        assertNotEquals(null, accountsManager);

        // different types -> returns false
        assertNotEquals(5, accountsManager);

        // different account list -> returns false
        accountsManager = new AccountsManager(TypicalAccounts.ACCOUNT_LIST, Index.fromOneBased(1));
        AccountsManager accountsManagerDiffAccounts = new AccountsManager();
        assertNotEquals(accountsManagerDiffAccounts, accountsManager);
    }
}
