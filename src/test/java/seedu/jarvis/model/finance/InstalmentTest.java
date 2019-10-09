package seedu.jarvis.model.finance;

import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.Installment;

/**
 * Tests instalment class.
 */
public class InstalmentTest {

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Installment(null, 0.0));
    }

    @Test
    public void editInstallment_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InstallmentStub().editDescription(null));
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super("Spotify subscription", 9.5);
        }
    }
}





