package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.storage.JsonAdaptedPurchase.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.TimeOfPurchase;


public class JsonAdaptedPurchaseTest {

    private static final TimeOfPurchase VALID_TIMEOFPURCHASE = TimeOfPurchase.generate();
    private static final String VALID_TIMEOFPURCHASESTRING = Long.toString(
            TimeOfPurchase.generate().getTimeOfPurchaseInMillisSinceEpoch());
    private static final JsonAdaptedFood VALID_JSONADAPTEDFOOD = new JsonAdaptedFood(CARBONARA);

    @Test
    public void toModelType_validPurchase_returnsPurchase() throws Exception {
        JsonAdaptedPurchase purchase = new JsonAdaptedPurchase(VALID_JSONADAPTEDFOOD, VALID_TIMEOFPURCHASESTRING);
        assertEquals(
                new Purchase(CARBONARA, VALID_TIMEOFPURCHASE),
                purchase.toModelType());
    }

    // Validity of Food and TimeOfPurchase guaranteed with tests already.

    @Test
    public void toModelType_nullFood_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(null, VALID_TIMEOFPURCHASESTRING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }

    @Test
    public void toModelType_nullTimeOfPurchase_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(VALID_JSONADAPTEDFOOD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeOfPurchase.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }

}
