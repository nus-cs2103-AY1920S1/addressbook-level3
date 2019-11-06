package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAccounts.ALISON_ACCOUNT;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.account.Account;
import seedu.address.testutil.AccountBuilder;

public class AccountBookTest {

    private final AccountBook accountBook = new AccountBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), accountBook.getList());
    }

    @Test
    public void hasAccount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> accountBook.hasAccount(null));
    }

    @Test
    public void hasAccount_accountNotInAccountBook_returnsFalse() {
        assertFalse(accountBook.hasAccount(ALISON_ACCOUNT));
    }

    @Test
    public void hasAccount_accountInAddressBook_returnsTrue() {
        accountBook.addAccount(ALISON_ACCOUNT);
        assertTrue(accountBook.hasAccount(ALISON_ACCOUNT));
    }

    @Test
    public void hasAccount_accountWithSameIdentityFieldsInAccountBook_returnsTrue() {
        accountBook.addAccount(ALISON_ACCOUNT);
        Account editedAlison = new AccountBuilder(ALISON_ACCOUNT).withUsername("alison")
                .build();
        assertTrue(accountBook.hasAccount(editedAlison));
    }
}
