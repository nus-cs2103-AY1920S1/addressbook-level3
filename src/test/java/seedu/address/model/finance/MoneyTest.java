package seedu.address.model.finance;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class MoneyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Money((String) null));
    }

    @Test
    public void constructor_invalidMoney_throwsIllegalArgumentException() {
        String invalidMoney = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidMoney));
    }

    @Test
    public void isValidAmount() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Money.isValidAmount(null));

        // invalid Amount numbers
        assertFalse(Money.isValidAmount("")); // empty string
        assertFalse(Money.isValidAmount(" ")); // spaces only
        assertFalse(Money.isValidAmount("Amount")); // non-numeric
        assertFalse(Money.isValidAmount("9011p041")); // alphabets within digits
        assertFalse(Money.isValidAmount("9312 1534")); // spaces within digits

        // valid Amount numbers
        assertTrue(Money.isValidAmount("911")); // exactly 3 numbers
        assertTrue(Money.isValidAmount("93121534.98"));
        assertTrue(Money.isValidAmount("124293842033123")); // long Amount numbers
    }
}
