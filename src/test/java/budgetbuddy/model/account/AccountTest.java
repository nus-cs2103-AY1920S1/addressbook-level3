package budgetbuddy.model.account;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.TransactionList;
import budgetbuddy.testutil.accountutil.AccountBuilder;

public class AccountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // all fields null
        assertThrows(NullPointerException.class, () -> new Account(
                null, null, null, 0));

        // name field null
        assertThrows(NullPointerException.class, () -> new AccountBuilder().withName(null).build());

        // description field null
        assertThrows(NullPointerException.class, () -> new AccountBuilder().withDescription(null).build());
    }

    @Test
    public void isSameAccount() {
        Account account = new Account(new Name("Japan trip"),
                new Description("Expense in Japan."), new TransactionList());

        // identical -> returns true
        Account accountCopy = new AccountBuilder(account).build();
        assertTrue(account.equals(accountCopy));

        // other different properties -> returns false

        Account accountOther = new AccountBuilder(account).withName("food").build();
        assertFalse(account.equals(accountOther));

        accountOther = new AccountBuilder(account).withDescription("blahblahblah").build();
        assertFalse(account.equals(accountOther));
    }
}
