package seedu.jarvis.model.finance.installment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;

public class InstallmentMoneyPaidTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InstallmentMoneyPaid(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new InstallmentMoneyPaid(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        //null amount
        assertThrows(NullPointerException.class, () -> InstallmentMoneyPaid.isValidAmount(null));

        //invalid amounts
        assertFalse(InstallmentMoneyPaid.isValidAmount("")); // empty string
        assertFalse(InstallmentMoneyPaid.isValidAmount(" ")); // spaces only
        assertFalse(InstallmentMoneyPaid.isValidAmount("9011p041")); // alphabets within digits

        //valid amounts
        assertTrue(InstallmentMoneyPaid.isValidAmount("13.50"));
        assertTrue(InstallmentMoneyPaid.isValidAmount("13"));
    }

    @Test
    public void isEquals() {
        InstallmentMoneyPaid amount1 = new InstallmentMoneyPaid("5.0");
        InstallmentMoneyPaid amount2 = new InstallmentMoneyPaid("5.0");
        assertTrue(amount1.equals(amount2));
    }

}
