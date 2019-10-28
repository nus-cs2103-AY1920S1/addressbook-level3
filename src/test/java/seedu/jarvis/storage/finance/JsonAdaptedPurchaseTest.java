package seedu.jarvis.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.finance.TypicalPurchases.DINNER_REEDZ;

import org.junit.jupiter.api.Test;

/**
 * Tests the behaviour of {@code JsonAdaptedPurchase}.
 */
public class JsonAdaptedPurchaseTest {

    @Test
    public void toModelType_returnsPurchase() throws Exception {
        JsonAdaptedPurchase jsonAdaptedPurchase = new JsonAdaptedPurchase(DINNER_REEDZ);
        assertEquals(DINNER_REEDZ, jsonAdaptedPurchase.toModelType());
    }
}
