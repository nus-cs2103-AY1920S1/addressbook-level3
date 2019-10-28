package seedu.jarvis.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.finance.TypicalInstallments.PHONE_BILL;

import org.junit.jupiter.api.Test;

/**
 * Tests the behaviour of {@code JsonAdaptedInstallment}.
 */
public class JsonAdaptedInstallmentTest {

    @Test
    public void toModelType_returnsInstallment() throws Exception {
        JsonAdaptedInstallment jsonAdaptedInstallment = new JsonAdaptedInstallment(PHONE_BILL);
        assertEquals(PHONE_BILL, jsonAdaptedInstallment.toModelType());
    }
}
