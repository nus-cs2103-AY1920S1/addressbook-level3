package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.finance.TypicalInstallments.PHONE_BILL;
import static seedu.jarvis.testutil.finance.TypicalInstallments.TRANSPORT_CONCESSION;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.installment.InstallmentDescription;
import seedu.jarvis.model.finance.installment.InstallmentMoneyPaid;

/**
 * Tests instalment class.
 */
public class InstallmentTest {

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Installment(new InstallmentDescription(null),
                new InstallmentMoneyPaid("0.0")));
    }

    @Test
    public void isSameInstallment() {
        Installment installment1 = new InstallmentStub();
        Installment installment2 = new InstallmentStub();
        assertTrue(installment1.equals(installment2));
        assertTrue(PHONE_BILL.isSameInstallment(PHONE_BILL));
        assertFalse(PHONE_BILL.isSameInstallment(TRANSPORT_CONCESSION));
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super(new InstallmentDescription("Spotify subscription"), new InstallmentMoneyPaid("9.5"));
        }
    }
}





