package seedu.savenus.storage;

import org.junit.jupiter.api.Test;
import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.TimeOfPurchase;

import static seedu.savenus.testutil.TypicalMenu.TONKATSU_RAMEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.storage.JsonAdaptedPurchase.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.savenus.testutil.Assert.assertThrows;

public class JsonAdaptedPurchaseTest {

    private static final String VALID_NAME = TONKATSU_RAMEN.getName().toString();
    private static final String VALID_PRICE = TONKATSU_RAMEN.getPrice().toString();
    private static final String VALID_TIMEOFPURCHASE = Long.toString(TimeOfPurchase.generate()
            .getTimeOfPurchaseInMillisSinceEpoch());


    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRICE = "+651234";
    private static final String INVALID_TIMEOFPURCHASE = "faketimeofpurchase";

    @Test
    public void toModelType_validPurchase_returnsPurchase() throws Exception {
        JsonAdaptedPurchase purchase = new JsonAdaptedPurchase(VALID_NAME, VALID_PRICE, VALID_TIMEOFPURCHASE);
        assertEquals(
                new Purchase(new Name(VALID_NAME), new Price(VALID_PRICE), new TimeOfPurchase(VALID_TIMEOFPURCHASE)),
                purchase.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(INVALID_NAME, VALID_PRICE, VALID_TIMEOFPURCHASE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(VALID_NAME, INVALID_PRICE, VALID_TIMEOFPURCHASE);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }
    
    @Test
    public void toModelType_invalidTimeOfPurchase_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(VALID_NAME, VALID_PRICE, INVALID_TIMEOFPURCHASE);
        String expectedMessage = TimeOfPurchase.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(null, VALID_PRICE, VALID_TIMEOFPURCHASE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }
    
    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(VALID_NAME, null, VALID_TIMEOFPURCHASE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }

    @Test
    public void toModelType_nullTimeOfPurchase_throwsIllegalValueException() {
        JsonAdaptedPurchase purchase =
                new JsonAdaptedPurchase(VALID_NAME, VALID_PRICE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeOfPurchase.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, purchase::toModelType);
    }
}
