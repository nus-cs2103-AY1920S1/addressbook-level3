package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.storage.JsonAdaptedWallet.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;

public class JsonAdaptedWalletTest {

    @Test
    public void toModelType_validWallet_returnsWallet() throws Exception {
        JsonAdaptedWallet wallet = new JsonAdaptedWallet("100", "30");
        assertEquals(new Wallet("100", "30"), wallet.toModelType());
    }

    @Test
    public void toModelType_invalidBudgetAmount_throwsIllegalValueException() {
        JsonAdaptedWallet wallet =
                new JsonAdaptedWallet("abc", "30");
        String expectedMessage = RemainingBudget.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, wallet::toModelType);
    }

    @Test
    public void toModelType_nullBudgetAmount_throwsIllegalValueException() {
        JsonAdaptedWallet wallet = new JsonAdaptedWallet(null, "30");
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RemainingBudget.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, wallet::toModelType);
    }

    @Test
    public void toModelType_invalidBudgetDuration_throwsIllegalValueException() {
        JsonAdaptedWallet wallet =
                new JsonAdaptedWallet("100", "abc");
        String expectedMessage = DaysToExpire.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, wallet::toModelType);
    }

    @Test
    public void toModelType_nullBudgetDuration_throwsIllegalValueException() {
        JsonAdaptedWallet wallet = new JsonAdaptedWallet("100", null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DaysToExpire.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, wallet::toModelType);
    }
}
